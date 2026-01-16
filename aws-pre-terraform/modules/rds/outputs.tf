output "rds_endpoint" {
  value = aws_db_instance.mysql.endpoint
}

output "mysql_db_port" {
  value = var.mysql_db_port
}

output "db_username" {
  value = var.db_username
}

output "db_password" {
  value = var.db_password
}
