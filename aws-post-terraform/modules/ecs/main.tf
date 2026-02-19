resource "aws_ecs_cluster" "ecs_cluster" {
  name = var.cluster_name

  tags = {
    Name        = "${var.project_name}-ecs-cluster"
    Environment = var.project_name
  }
}

resource "aws_ecs_cluster_capacity_providers" "ecs_cluster_capacity_providers" {
  cluster_name       = aws_ecs_cluster.ecs_cluster.name
  capacity_providers = ["FARGATE", "FARGATE_SPOT"]

  default_capacity_provider_strategy {
    capacity_provider = "FARGATE"
    weight            = 1
  }
}

resource "aws_ecs_task_definition" "zipkin_ecs_task_definition" {
  family                   = var.zipkin_name
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
      name  = var.zipkin_name
      image = var.zipkin_repository_url
      environment = [
        {
          name  = "AWS_ALB_DNS"
          value = tostring(var.alb_dns)
        },
        {
          name  = "ZIPKIN_PORT"
          value = tostring(var.zipkin_port)
        }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = var.cloudwatch_log_group_name
          awslogs-region        = var.aws_region
          awslogs-stream-prefix = "ecs"
        }
      }
      portMappings = [{ containerPort = var.zipkin_port }]
    }
  ])

  tags = {
    Name        = "${var.project_name}-ecs-task-definition-zipkin"
    Environment = var.project_name
  }
}

resource "aws_ecs_service" "zipkin_ecs_service" {
  name            = "${var.project_name}-${var.zipkin_name}"
  cluster         = aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.zipkin_ecs_task_definition.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = [var.private_subnets]
    security_groups = [var.ecs_sg_id]
  }

  load_balancer {
    target_group_arn = var.zipkin_alb_target_group_arn
    container_name   = var.zipkin_name
    container_port   = var.zipkin_port
  }

  tags = {
    Name        = "${var.project_name}-ecs-service-zipkin"
    Environment = var.project_name
  }
}

resource "aws_ecs_task_definition" "web_server_ecs_task_definition" {
  family                   = var.web_server_name
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
      name  = var.web_server_name
      image = var.web_server_repository_url
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = var.cloudwatch_log_group_name
          awslogs-region        = var.aws_region
          awslogs-stream-prefix = "ecs"
        }
      }
      portMappings = [{ containerPort = var.web_server_port }]
    }
  ])

  tags = {
    Name        = "${var.project_name}-ecs-task-definition-web-server"
    Environment = var.project_name
  }
}

resource "aws_ecs_service" "web_server_ecs_service" {
  name            = "${var.project_name}-${var.web_server_name}"
  cluster         = aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.web_server_ecs_task_definition.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = [var.private_subnets]
    security_groups = [var.ecs_sg_id]
  }

  load_balancer {
    target_group_arn = var.web_server_alb_target_group_arn
    container_name   = var.web_server_name
    container_port   = var.web_server_port
  }

  tags = {
    Name        = "${var.project_name}-ecs-service-web-server"
    Environment = var.project_name
  }
}

resource "aws_ecs_task_definition" "registry_service_ecs_task_definition" {
  family                   = var.registry_service_name
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
      name  = var.registry_service_name
      image = var.registry_service_repository_url
      environment = [
        {
          name  = "AWS_ALB_DNS"
          value = tostring(var.alb_dns)
        },
        {
          name  = "REGISTRY_SERVICE_PORT"
          value = tostring(var.registry_service_port)
        }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = var.cloudwatch_log_group_name
          awslogs-region        = var.aws_region
          awslogs-stream-prefix = "ecs"
        }
      }
      portMappings = [{ containerPort = var.registry_service_port }]
    }
  ])

  tags = {
    Name        = "${var.project_name}-ecs-task-definition-registry-service"
    Environment = var.project_name
  }
}

resource "aws_ecs_service" "registry_service_ecs_service" {
  name            = "${var.project_name}-${var.registry_service_name}"
  cluster         = aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.registry_service_ecs_task_definition.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = [var.private_subnets]
    security_groups = [var.ecs_sg_id]
  }

  load_balancer {
    target_group_arn = var.registry_service_alb_target_group_arn
    container_name   = var.registry_service_name
    container_port   = var.registry_service_port
  }

  tags = {
    Name        = "${var.project_name}-ecs-service-registry-service"
    Environment = var.project_name
  }
}

resource "aws_ecs_task_definition" "config_server_ecs_task_definition" {
  family                   = var.config_server_name
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
      name  = var.config_server_name
      image = var.config_server_repository_url
      environment = [
        {
          name  = "AWS_ALB_DNS"
          value = tostring(var.alb_dns)
        },
        {
          name  = "REGISTRY_SERVICE_PORT"
          value = tostring(var.registry_service_port)
        },
        {
          name  = "CONFIG_SERVER_PORT"
          value = tostring(var.config_server_port)
        },
        {
          name  = "API_GATEWAY_PORT"
          value = tostring(var.api_gateway_port)
        },
        {
          name  = "ZIPKIN_PORT"
          value = tostring(var.zipkin_port)
        }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = var.cloudwatch_log_group_name
          awslogs-region        = var.aws_region
          awslogs-stream-prefix = "ecs"
        }
      }
      portMappings = [{ containerPort = var.config_server_port }]
    }
  ])

  tags = {
    Name        = "${var.project_name}-ecs-task-definition-config-server"
    Environment = var.project_name
  }
}

resource "aws_ecs_service" "config_server_ecs_service" {
  name            = "${var.project_name}-${var.config_server_name}"
  cluster         = aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.config_server_ecs_task_definition.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = [var.private_subnets]
    security_groups = [var.ecs_sg_id]
  }

  load_balancer {
    target_group_arn = var.config_server_alb_target_group_arn
    container_name   = var.config_server_name
    container_port   = var.config_server_port
  }

  tags = {
    Name        = "${var.project_name}-ecs-service-config-server"
    Environment = var.project_name
  }
}

resource "aws_ecs_task_definition" "api_gateway_ecs_task_definition" {
  family                   = var.api_gateway_name
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
      name  = var.api_gateway_name
      image = var.api_gateway_repository_url
      environment = [
        {
          name  = "AWS_ALB_DNS"
          value = tostring(var.alb_dns)
        },
        {
          name  = "REGISTRY_SERVICE_PORT"
          value = tostring(var.registry_service_port)
        },
        {
          name  = "CONFIG_SERVER_PORT"
          value = tostring(var.config_server_port)
        },
        {
          name  = "API_GATEWAY_PORT"
          value = tostring(var.api_gateway_port)
        },
        {
          name  = "ZIPKIN_PORT"
          value = tostring(var.zipkin_port)
        }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = var.cloudwatch_log_group_name
          awslogs-region        = var.aws_region
          awslogs-stream-prefix = "ecs"
        }
      }
      portMappings = [{ containerPort = var.api_gateway_port }]
    }
  ])

  tags = {
    Name        = "${var.project_name}-ecs-task-definition-api-gateway"
    Environment = var.project_name
  }
}

resource "aws_ecs_service" "api_gateway_ecs_service" {
  name            = "${var.project_name}-${var.api_gateway_name}"
  cluster         = aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.api_gateway_ecs_task_definition.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = [var.private_subnets]
    security_groups = [var.ecs_sg_id]
  }

  load_balancer {
    target_group_arn = var.api_gateway_alb_target_group_arn
    container_name   = var.api_gateway_name
    container_port   = var.api_gateway_port
  }

  tags = {
    Name        = "${var.project_name}-ecs-service-api-gateway"
    Environment = var.project_name
  }
}
