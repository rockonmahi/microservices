resource "aws_eks_cluster" "eks_cluster" {
  name     = "${var.project_name}-${var.cluster_name}"
  role_arn = var.eks_cluster_role
  version  = "1.29"

  vpc_config {
    subnet_ids = var.private_subnets_id
  }

  depends_on = [
    var.eks_worker_node_policy
  ]
}
resource "aws_eks_node_group" "eks_node_group" {
  cluster_name    = aws_eks_cluster.eks_cluster.name
  node_group_name = "default-ng"
  node_role_arn  = var.eks_node_role
  subnet_ids     = var.private_subnets_id

  scaling_config {
    desired_size = 2
    min_size     = 1
    max_size     = 3
  }

  instance_types = ["t3.medium"]

  depends_on = [
    var.eks_worker_node_policy,
    var.eks_cni_policy,
    var.ecr_read
  ]
}
