package it.gov.pagopa.reportingorgsenrollment.service;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableQuery;

import it.gov.pagopa.reportingorgsenrollment.entity.OrganizationEntity;
import it.gov.pagopa.reportingorgsenrollment.exception.AppError;
import it.gov.pagopa.reportingorgsenrollment.exception.AppException;
import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;
import it.gov.pagopa.reportingorgsenrollment.util.AzuriteStorageUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EnrollmentsService {
	
	@Autowired
    private ModelMapper modelMapper;

	private String storageConnectionString;
	private String organizationsTable;
	
	public EnrollmentsService(@Value("${reporting.sa.connection}") String storageConnectionString, @Value("${organizations.table}") String organizationsTable)  {
		super();
		this.storageConnectionString = storageConnectionString;
		this.organizationsTable = organizationsTable;
        try {
        	AzuriteStorageUtil azuriteStorageUtil = new AzuriteStorageUtil(storageConnectionString);
			azuriteStorageUtil.createTable(organizationsTable);
		} catch (InvalidKeyException | URISyntaxException | StorageException e) {
			log.error("Error in environment initializing", e);
		}
	}


    public OrganizationModelResponse createOrganization(String organizationFiscalCode) {
    	OrganizationEntity resultOrganizationEntity = null;
		try {
			CloudTable table = CloudStorageAccount.parse(storageConnectionString).createCloudTableClient()
			        .getTableReference(this.organizationsTable);
			resultOrganizationEntity = (OrganizationEntity) table.execute(TableOperation.insert(new OrganizationEntity(organizationFiscalCode, LocalDateTime.now().toString()))).getResult();
		} catch (InvalidKeyException | URISyntaxException | StorageException e) {
			if (e instanceof StorageException && ((StorageException) e).getHttpStatusCode() == HttpStatus.CONFLICT.value()) {
				throw new AppException(AppError.ORGANIZATION_DUPLICATED, organizationFiscalCode);
			}
			// unexpected error
			log.error("Error in processing create organization", e);
			throw new AppException(AppError.INTERNAL_ERROR, organizationFiscalCode);
		} 
		return modelMapper.map(resultOrganizationEntity, OrganizationModelResponse.class);
        
    }
    
    public void removeOrganization(String organizationFiscalCode) {
		try {
			CloudTable table = CloudStorageAccount.parse(storageConnectionString).createCloudTableClient()
			        .getTableReference(this.organizationsTable);
			table.execute(TableOperation.delete(new OrganizationEntity(organizationFiscalCode)));
		} catch (InvalidKeyException | URISyntaxException | StorageException e) {
			if (e instanceof StorageException && ((StorageException) e).getHttpStatusCode() == HttpStatus.NOT_FOUND.value()) {
				throw new AppException(AppError.ORGANIZATION_NOT_FOUND, organizationFiscalCode);
			}
			// unexpected error
			log.error("Error in processing delete organization", e);
			throw new AppException(AppError.INTERNAL_ERROR, organizationFiscalCode);
		} 
        
    }
    
    public OrganizationModelResponse getOrganization(String organizationFiscalCode) {
    	OrganizationEntity resultOrganizationEntity = null;
		try {
			CloudTable table = CloudStorageAccount.parse(storageConnectionString).createCloudTableClient()
			        .getTableReference(this.organizationsTable);
			resultOrganizationEntity = Optional.ofNullable(
					(OrganizationEntity) table.execute(TableOperation.retrieve(OrganizationEntity.ORGANIZATION_KEY, organizationFiscalCode, OrganizationEntity.class)).getResult())
			.orElseThrow(() -> new AppException(AppError.ORGANIZATION_NOT_FOUND, organizationFiscalCode));
			
		} catch (InvalidKeyException | URISyntaxException | StorageException e) {
			// unexpected error
			log.error("Error in processing get organization", e);
			throw new AppException(AppError.INTERNAL_ERROR, organizationFiscalCode);
		} 
		return modelMapper.map(resultOrganizationEntity, OrganizationModelResponse.class);
    }
    
    public List<OrganizationModelResponse> getOrganizations() {
    	Spliterator<OrganizationEntity> resultOrganizationEntityList = null;
		try {
			CloudTable table = CloudStorageAccount.parse(storageConnectionString).createCloudTableClient()
			        .getTableReference(this.organizationsTable);
			resultOrganizationEntityList = 
					table.execute(TableQuery.from(OrganizationEntity.class).where((TableQuery.generateFilterCondition("PartitionKey", TableQuery.QueryComparisons.EQUAL, OrganizationEntity.ORGANIZATION_KEY)))).spliterator();
			
		} catch (InvalidKeyException | URISyntaxException | StorageException e) {
			// unexpected error
			log.error("Error in processing get organizations list", e);
			throw new AppException(AppError.INTERNAL_ERROR, "ALL");
		} 
		return this.mapList(resultOrganizationEntityList, OrganizationModelResponse.class) ;
        
    }
    
    private <S, T> List<T> mapList(Spliterator<S> source, Class<T> targetClass) {
		return StreamSupport.stream(source,false) 
		  .map(element -> modelMapper.map(element, targetClass))
	      .collect(Collectors.toList());
	}

}
