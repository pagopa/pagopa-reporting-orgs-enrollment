#!/bin/bash

# example: sh ./run_integration_test.sh <local|dev|uat> <subkey>
set -e

export ENV=$1
export API_SUBSCRIPTION_KEY=$2

# run integration tests
cd ./src || exit
yarn install
yarn test:"$1"
