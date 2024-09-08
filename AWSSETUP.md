# Deploy to AWS
## 1.Build and Tag Docker Image:
- After successfully building your Docker image as per the instructions in [DEVSETUP.md](DEVSETUP.md), tag the image for AWS ECR.
## 2. Push Docker Image to Amazon ECR:
- Log in to AWS:
  - Navigate to the [Amazon ECR console](https://console.aws.amazon.com/ecr/home) and create a new repository (e.g., coffee-shop-chain-repo).
- Authenticate Docker to ECR:
```shell
aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin <account-id>.dkr.ecr.<region>.amazonaws.com
```
- Tag the Docker Image:
```shell
docker tag coffee-shop-chain:latest <account-id>.dkr.ecr.<region>.amazonaws.com/coffee-shop-chain-repo:latest
```
- Push the Docker Image:
```shell
docker push <account-id>.dkr.ecr.<region>.amazonaws.com/coffee-shop-chain-repo:latest
```
## 3.Deploy Docker Image to AWS:
- You can now use the image in your ECR repository to deploy to AWS services like Amazon ECS, EKS, EC2...