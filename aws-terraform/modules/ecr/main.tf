resource "aws_ecr_repository" "ecr_repository" {
  name = var.repo_name

  tags = {
    Name = "${var.project_name}-ecr"
    Environment = var.project_name
  }
}
