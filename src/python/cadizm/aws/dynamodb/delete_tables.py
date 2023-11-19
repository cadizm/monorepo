from pprint import pprint

from config import client
from schema import TABLE_NAME


existing_table_names = client.list_tables().get("TableNames", [])
tables_to_delete = [TABLE_NAME]

for table_name in tables_to_delete:
    if table_name in existing_table_names:
        pprint(client.delete_table(TableName=table_name))

    else:
        print(f'\nTable "{table_name}" does not exist\n')
