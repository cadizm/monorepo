# Lightsail regions
#  https://docs.aws.amazon.com/en_us/lightsail/latest/userguide/understanding-regions-and-availability-zones-in-amazon-lightsail.html
# Lightsail blueprints (images)
#   aws lightsail get-blueprints
# Lightsail bundles
#   aws lightsail get-bundles
# Manage SSH keys (key pairs)
#  https://lightsail.aws.amazon.com/ls/webapp/account/keys
resource "aws_lightsail_instance" "mcadiz" {
  name              = "mcadiz_instance"
  availability_zone = "us-east-1b"
  blueprint_id      = "ubuntu_22_04"
  bundle_id         = "nano_3_0"
  key_pair_name     = "us-east-1-key-pair-1"
  user_data         = "sudo apt update --yes && sudo apt install --yes software-properties-common && sudo add-apt-repository --yes --update ppa:ansible/ansible && sudo apt install --yes ansible"
}

resource "aws_lightsail_static_ip" "mcadiz" {
  name = "mcadiz_static_ip"
}

resource "aws_lightsail_static_ip_attachment" "mcadiz" {
  static_ip_name = aws_lightsail_static_ip.mcadiz.id
  instance_name  = aws_lightsail_instance.mcadiz.id
}

# Lightsail instance nameservers
#   https://lightsail.aws.amazon.com/ls/webapp/domains/mcadiz-com
# Update DNS nameservers at registrar (e.g. Namecheap)
resource "aws_lightsail_domain" "mcadiz" {
  domain_name = "mcadiz.com"
}

resource "aws_lightsail_domain_entry" "mcadiz" {
  domain_name = aws_lightsail_domain.mcadiz.domain_name
  name        = ""
  type        = "A"
  target      = aws_lightsail_static_ip.mcadiz.ip_address
}

output "mcadiz_ipv4_addr" {
  value = aws_lightsail_static_ip.mcadiz.ip_address
}
