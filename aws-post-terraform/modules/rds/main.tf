resource "aws_db_subnet_group" "mysql_subnet_group" {
  name       = "${var.project_name}-mysql-subnet-group"
  subnet_ids = var.subnets

  tags = {
    Name        = "${var.project_name}-mysql-subnet-group"
    Environment = var.project_name
  }
}

resource "aws_db_instance" "mysql" {
  identifier              = "${var.project_name}-mysql-db"
  engine                  = "mysql"
  engine_version          = "8.0"
  instance_class          = "db.t3.micro"
  allocated_storage       = 20
  max_allocated_storage   = 100
  storage_type            = "gp3"
  db_name                 = var.db_name
  username                = var.db_username
  password                = var.db_password
  db_subnet_group_name    = aws_db_subnet_group.mysql_subnet_group.name
  vpc_security_group_ids  = [var.rds_sg_id]
  multi_az                = false
  publicly_accessible     = false
  backup_retention_period = 7
  backup_window           = "03:00-04:00"
  skip_final_snapshot     = true
  deletion_protection     = false

  tags = {
    Name        = "${var.project_name}--mysql-rds"
    Environment = var.project_name
  }
}