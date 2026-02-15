resource "aws_iam_openid_connect_provider" "github" {
  name = "${var.project_name}-github"
  url            = "https://token.actions.githubusercontent.com"
  client_id_list = ["sts.amazonaws.com"]

  tags = {
    Name        = "${var.project_name}-github"
    Environment = var.project_name
  }
}

resource "aws_iam_role" "github_actions" {
  name = "${var.project_name}-github-actions"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect = "Allow"
      Principal = {
        Federated = aws_iam_openid_connect_provider.github.arn
      }
      Action = "sts:AssumeRoleWithWebIdentity"
      "Condition" : {
        "StringEquals" : {
          "token.actions.githubusercontent.com:aud" : [
            "sts.amazonaws.com"
          ]
        },
        "StringLike" : {
          "token.actions.githubusercontent.com:sub" : [
            "repo:rockonmahi/microservices:ref:refs/heads/main",
            "repo:rockonmahi/microservices:ref:refs/heads/main"
          ]
        }
      }
    }]
  })

  tags = {
    Name        = "${var.project_name}-github-actions"
    Environment = var.project_name
  }
}

resource "aws_iam_role_policy_attachment" "ecs_full_access_github" {
  name = "${var.project_name}-ecs-full-access-github"
  role       = aws_iam_role.github_actions.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonECS_FullAccess"
  tags = {
    Name        = "${var.project_name}-ecs-full-access-github"
    Environment = var.project_name
  }
}

resource "aws_iam_role_policy_attachment" "admin_access_github" {
  name = "${var.project_name}-admin-access-github"
  role       = aws_iam_role.github_actions.name
  policy_arn = "arn:aws:iam::aws:policy/AdministratorAccess"
  tags = {
    Name        = "${var.project_name}-admin-access-github"
    Environment = var.project_name
  }
}

resource "aws_iam_role" "ecs_execution_role" {
  name = "${var.project_name}-ecs-execution-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect    = "Allow"
      Principal = { Service = "ecs-tasks.amazonaws.com" }
      Action    = "sts:AssumeRole"
    }]
  })

  tags = {
    Name        = "${var.project_name}-ecs-execution-role"
    Environment = var.project_name
  }
}

resource "aws_iam_role_policy_attachment" "ecs_task_execution_role" {
  name = "${var.project_name}-ecs-task-execution-role"
  role       = aws_iam_role.ecs_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
  tags = {
      Name        = "${var.project_name}-ecs-task-execution-role"
      Environment = var.project_name
  }
}

resource "aws_iam_role_policy_attachment" "ec2_container_registry_read" {
  name = "${var.project_name}-ec2-container-registry-read"
  role       = aws_iam_role.eks_node_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
  tags = {
        Name        = "${var.project_name}-ec2-container-registry-read"
        Environment = var.project_name
  }
}