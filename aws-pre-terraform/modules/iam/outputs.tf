output "eks_cluster_role" {
  value = aws_iam_role.eks_cluster_role.arn
}

output "eks_node_role" {
  value = aws_iam_role.eks_node_role.arn
}

output "eks_worker_node_policy" {
  value = aws_iam_role_policy_attachment.eks_worker_node_policy
}

output "eks_cni_policy" {
  value = aws_iam_role_policy_attachment.eks_cni_policy
}

output "ec2_container_registry_read" {
  value = aws_iam_role_policy_attachment.ec2_container_registry_read
}

output "ecs_execution_role" {
  value = aws_iam_role.ecs_execution_role.arn
}