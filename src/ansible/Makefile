all:
	@echo Targets
	@cat Makefile | grep ':' | grep -v 'all:' | awk -F ':' '{print " ", $$1}'

setup: install-packages setup-docker add-superusers setup-cadizm setup-nginx enable-ssl

install-packages:
	ansible-playbook -i hosts --limit=mcadiz --user=ubuntu playbooks/install-packages.yml

setup-docker: install-packages
	ansible-playbook -i hosts --limit=mcadiz --user=ubuntu playbooks/setup-docker.yml

add-superusers: setup-docker
	ansible-playbook -i hosts --limit=mcadiz --user=ubuntu playbooks/add-superusers.yml

setup-cadizm: add-superusers
	ansible-playbook -i hosts --limit=mcadiz --user=cadizm playbooks/setup-cadizm.yml

setup-nginx: setup-cadizm
	ansible-playbook -i hosts --limit=mcadiz --user=cadizm playbooks/setup-nginx.yml

enable-ssl: #setup-nginx
	ansible-playbook -i hosts --limit=mcadiz --user=cadizm playbooks/enable-ssl.yml
