resource "aws_efs_file_system" "mongo_db_efs_file_system" {
  creation_token = "${var.project_name}-mongo-db-efs"

  tags = {
    Name        = "${var.project_name}-efs-mongo-db"
    Environment = var.project_name
  }
}

resource "aws_efs_mount_target" "mongo_db_efs_mount_target" {
  file_system_id  = aws_efs_file_system.mongo_db_efs_file_system.id
  for_each        = toset(var.private_subnets)
  subnet_id       = each.value
  security_groups = [var.database_sg_id]
}
