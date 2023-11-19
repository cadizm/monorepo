from boto3.session import Session

ENV = "local"

# -------------------------------------------------------------------------------------------------
#   Configuration
# -------------------------------------------------------------------------------------------------

# Don't need real credentials for local dynamodb
#   - https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html
#
# Passing credentials as parameters
# https://boto3.amazonaws.com/v1/documentation/api/latest/guide/credentials.html#passing-credentials-as-parameters
param_credentials = dict(
    aws_access_key_id="fake-key",
    aws_secret_access_key="fake-secret-key",
    aws_session_token="fake-token",
)

# AWS Single Sign-On Provider (SSO)
# https://boto3.amazonaws.com/v1/documentation/api/latest/guide/credentials.html#aws-single-sign-on-provider-sso
sso_credentials = dict(profile_name="some-profile")


# -------------------------------------------------------------------------------------------------
#   Configuration
# -------------------------------------------------------------------------------------------------

local_config = dict(
    service_name="dynamodb",
    region_name="us-west-2",
    endpoint_url="http://localhost:8003",
)

# Amazon DynamoDB endpoints and quotas
# https://docs.aws.amazon.com/general/latest/gr/ddb.html
us_east_1_config = dict(
    service_name="dynamodb",
    region_name="us-east-1",
    endpoint_url="https://dynamodb.us-east-1.amazonaws.com",
)


# -------------------------------------------------------------------------------------------------
#   Default session, client, and dynamodb instances
# -------------------------------------------------------------------------------------------------

default_credentials = param_credentials
default_config = local_config

session = Session(**default_credentials)
client = session.client(**default_config)
dynamodb = session.resource(**default_config)
