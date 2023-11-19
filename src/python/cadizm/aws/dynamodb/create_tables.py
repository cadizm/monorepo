from pprint import pprint

from config import client
from schema import table_name_schema_map


existing_table_names = client.list_tables().get("TableNames", [])

for table_name, schema in table_name_schema_map.items():
    if table_name not in existing_table_names:
        response = client.create_table(**schema)
        pprint(response)

    else:
        print(f'\nTable "{table_name}" already exists\n')
        pprint(client.describe_table(TableName=table_name))
