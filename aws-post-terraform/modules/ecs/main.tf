resource "aws_ecs_cluster" "ecs_cluster" {
  name = "${var.project_name}-${var.cluster_name}"

  tags = {
    Name        = "${var.project_name}-ecs-cluster"
    Environment = var.project_name
  }
}

resource "aws_ecs_cluster_capacity_providers" "ecs_cluster_capacity_providers" {
  cluster_name = aws_ecs_cluster.ecs_cluster.name

  capacity_providers = ["FARGATE", "FARGATE_SPOT"]

  default_capacity_provider_strategy {
    capacity_provider = "FARGATE"
    weight            = 1
  }
}

resource "aws_ecs_task_definition" "ecs_web_server_task_definition" {
  family                   = var.service_name
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = 1024
  memory                   = 3072
  execution_role_arn       = var.ecs_execution_role

  runtime_platform {
    operating_system_family = "LINUX"
    cpu_architecture        = "X86_64"
  }

  container_definitions = jsonencode([
    {
      name         = var.service_name
      image        = var.web_server_repository_url
      portMappings = [{ containerPort = 80 }]
    }
  ])

  tags = {
    Name        = "${var.project_name}-ecs-task-definition"
    Environment = var.project_name
  }
}

resource "aws_ecs_service" "ecs_service_web_server" {
  name            = "${var.project_name}-${var.service_name}"
  cluster         = aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.ecs_web_server_task_definition.arn
  desired_count   = 2
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = [var.private_subnets,var.public_subnets]
    security_groups = [var.ecs_sg_id]
    /* assign_public_ip = true*/
  }

  load_balancer {
    target_group_arn = var.web_server_target_group
    container_name   = var.service_name
    container_port   = 80
  }

  tags = {
    Name        = "${var.project_name}-ecs-service"
    Environment = var.project_name
  }
}