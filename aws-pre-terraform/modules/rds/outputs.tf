output "rds_endpoint" {
  value = aws_db_instance.mysql.endpoint
}

output "rds_port" {
  value = aws_db_instance.mysql.port
}

output "db_username" {
  value = var.db_username
}

output "db_password" {
  value = var.db_password
}
