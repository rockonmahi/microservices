resource "aws_cloudwatch_log_group" "cloudwatch_log_group" {
  name              = "/ecs/${var.project_name}"
  retention_in_days = 7

  tags = {
    Name        = "${var.project_name}-ecs-logs"
    Environment = var.project_name
  }
}