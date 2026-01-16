resource "aws_efs_file_system" "mongo_db_efs_file_system" {
  creation_token = "mongo-db-efs"

  tags = {
    Name        = "${var.project_name}-efs-mongo-db"
    Environment = var.project_name
  }
}

resource "aws_efs_mount_target" "mongo_db_efs_mount_target" {
  file_system_id  = aws_efs_file_system.mongo_db_efs_file_system.id
  subnet_id       = var.private_subnets
  security_groups = [var.database_sg_id]
}
