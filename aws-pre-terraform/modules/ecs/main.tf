resource "aws_ecs_cluster" "ecs_cluster" {
  name = "${var.project_name}-${var.cluster_name}"

  tags = {
    Name = "${var.project_name}-ecs-cluster"
    Environment = var.project_name
  }
}

resource "aws_ecs_task_definition" "ecs_task_definition" {
  family                   = var.service_name
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu    = 512
  memory = 1024
  execution_role_arn = var.ecs_execution_role

  container_definitions = jsonencode([
    {
      name  = var.service_name
      image = var.web_server_repository_url
      portMappings = [{ containerPort = 80 }]
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
    target_group_arn = var.web_server_target_group
    container_name   = var.service_name
    container_port   = 80
  }

  tags = {
    Name = "${var.project_name}-ecs-service"
    Environment = var.project_name
  }
}