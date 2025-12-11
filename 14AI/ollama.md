docker-compose.yml

```yaml
version: '3'

services:
  ollama:
    image: ollama/ollama
    container_name: ollama-container
    ports:
      - "11434:11434"
#    deploy:
#      resources:
#        reservations:
#          devices:
#            - driver: nvidia
#              count: all
#              capabilities: [gpu]
    volumes:
      - ollama_data:/root/.ollama
    environment:
      - OLLAMA_HOST=0.0.0.0  # 允许外部访问
      - OLLAMA_MODELS="https://mirror.ghproxy.com/https://raw.githubusercontent.com/ollama/ollama/main/docs/models.json" 
      - OLLAMA_BASE_URL="https://ollama.com.cn/api" # 国内镜像源

volumes:
  ollama_data:
```

