output "alb_dns" {
  value = aws_lb.alb.dns_name
}

output "alb_web_server_target_group_arn" {
  value = aws_lb_target_group.alb_web_server_target_group.arn
}

output "alb_api_gateway_target_group_arn" {
  value = aws_lb_target_group.alb_api_gateway_target_group.arn
}