output "alb_dns" {
  value = aws_lb.alb.dns_name
}

output "mongo_db_alb_target_group_arn" {
  value = aws_lb_target_group.mongo_db_alb_target_group.arn
}

output "zipkin_alb_target_group_arn" {
  value = aws_lb_target_group.zipkin_alb_target_group.arn
}

output "web_server_alb_target_group_arn" {
  value = aws_lb_target_group.web_server_alb_target_group.arn
}

output "registry_service_alb_target_group_arn" {
  value = aws_lb_target_group.registry_service_alb_target_group.arn
}

output "config_server_alb_target_group_arn" {
  value = aws_lb_target_group.config_server_alb_target_group.arn
}

output "api_gateway_alb_target_group_arn" {
  value = aws_lb_target_group.api_gateway_alb_target_group.arn
}