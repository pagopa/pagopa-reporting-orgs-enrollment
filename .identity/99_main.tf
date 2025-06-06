terraform {
  required_version = ">=1.3.0"

  required_providers {
    azuread = {
      source  = "hashicorp/azuread"
      version = "~> 2.53"
    }
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.117"
    }
    github = {
      source  = "integrations/github"
      version = "5.18.3"
    }
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "= 2.36.0"
    }
  }

  backend "azurerm" {}
}

provider "azurerm" {
  features {}
}

provider "github" {
  owner          = "pagopa"
  write_delay_ms = "200"
  read_delay_ms  = "200"
}

data "azurerm_subscription" "current" {}

data "azurerm_client_config" "current" {}

provider "kubernetes" {
  config_path = "${var.k8s_kube_config_path_prefix}/config-${local.aks_cluster.name}"
}
