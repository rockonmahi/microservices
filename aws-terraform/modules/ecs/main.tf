resource "aws_ecs_cluster" "ecs_cluster" {
  name = "${var.project_name}-microservice-cluster"

  tags = {
    Name = "${var.project_name}-ecs-cluster"
    Environment = var.project_name
  }
}

resource "aws_iam_role" "iam_role" {
  name = "ecsExecutionRole"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect = "Allow"
      Principal = { Service = "ecs-tasks.amazonaws.com" }
      Action = "sts:AssumeRole"
    }]
  })

  tags = {
    Name = "${var.project_name}-iam-role"
    Environment = var.project_name
  }
}

resource "aws_iam_role_policy_attachment" "execution" {
  role       = aws_iam_role.iam_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_ecs_task_definition" "ecs_task_definition" {
  family                   = var.service_name
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu    = 512
  memory = 1024
  execution_role_arn = aws_iam_role.iam_role.arn

  container_definitions = jsonencode([
    {
      name  = var.service_name
      image = var.image_url
      portMappings = [{ containerPort = 8080 }]
    }
  ])

  tags = {
    Name = "${var.project_name}-ecs-task-definition"
    Environment = var.project_name
  }
}

resource "aws_ecs_service" "ecs_service" {
  name            = "${var.project_name}-${var.service_name}"
  cluster         = aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.ecs_task_definition.arn
  desired_count   = 2
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = [var.private_subnets]
    security_groups = [var.ecs_sg_id]
  }

  load_balancer {
    target_group_arn = var.target_group
    container_name   = var.service_name
    container_port   = 8080
  }

  tags = {
    Name = "${var.project_name}-ecs-service"
    Environment = var.project_name
  }
}