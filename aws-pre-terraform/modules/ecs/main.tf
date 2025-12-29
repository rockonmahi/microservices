resource "aws_ecs_cluster" "ecs_cluster" {
  name = "${var.project_name}-${var.cluster_name}"

  tags = {
    Name = "${var.project_name}-ecs-cluster"
    Environment = var.project_name
  }
}