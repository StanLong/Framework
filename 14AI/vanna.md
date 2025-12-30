# vanna

# CentOS7.9 下安装 vanna

```shell
# 创建虚拟环境（以 venv 为例）
python3.9 -m venv vanna-env

# 激活虚拟环境
# Linux/Mac
source vanna-env/bin/activate

# 安装核心包
pip install vanna
# 或者安装全量包 （推荐，包含所有依赖，支持主流大模型 / 数据库）
pip install "vanna[all]"
# 按照官方文档，安装如下包
pip install 'vanna[chromadb,ollama,mysql]'

# 验证
python3.9 -c "import vanna; print(f'Vanna 版本：{vanna.__version__}')"
```



```python
import vanna
from vanna.legacy.remote import VannaDefault
vn = VannaDefault(
        model='gpt-3.5-turbo', 
        api_key='sk-eKb7lMsto2wBZpv3E7C9BdA2F501410790AdC5A7DfF19dAa'
)
vn.connect_to_mysql(host='192.168.145.15',db_name='demo',user='root','password'='123456', port=3306)
from vanna.legacy.flask import VannaFlaskApp
VannaFlaskApp(vn).run()
```

