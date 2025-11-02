#!/bin/bash

echo "=== 后端代码行数统计 ==="
echo

# 统计函数：计算有效代码行数（排除空行和注释行）
count_effective_lines() {
    local dir=$1
    local name=$2
    
    if [ -d "$dir" ]; then
        local total=$(find "$dir" -name "*.java" -type f -exec grep -v '^\s*$\|^\s*//' {} \; | grep -v '^\s*/\*\|^\s*\*' | wc -l)
        local files=$(find "$dir" -name "*.java" -type f | wc -l)
        echo "$name: $total 行有效代码 ($files 个文件)"
        return $total
    else
        echo "$name: 目录不存在"
        return 0
    fi
}

# 统计各个模块
echo "1. Controller层:"
count_effective_lines "src/main/java/com/course/controller" "Controller"
controller_lines=$?

echo
echo "2. Service层:"
count_effective_lines "src/main/java/com/course/service" "Service"
service_lines=$?

echo
echo "3. DAO层:"
count_effective_lines "src/main/java/com/course/dao" "DAO"
dao_lines=$?

echo
echo "4. Mapper层:"
count_effective_lines "src/main/java/com/course/mapper" "Mapper"
mapper_lines=$?

echo
echo "5. POJO层:"
count_effective_lines "src/main/java/com/course/pojo" "POJO"
pojo_lines=$?

echo
echo "6. Utils工具类:"
count_effective_lines "src/main/java/com/course/utils" "Utils"
utils_lines=$?

echo
echo "7. Interceptor拦截器:"
count_effective_lines "src/main/java/com/course/interceptor" "Interceptor"
interceptor_lines=$?

echo
echo "8. Schedule定时任务:"
count_effective_lines "src/main/java/com/course/schedule" "Schedule"
schedule_lines=$?

echo
echo "9. Model模型:"
count_effective_lines "src/main/java/com/course/model" "Model"
model_lines=$?

echo
echo "10. 主启动类:"
if [ -f "src/main/java/com/course/Application.java" ]; then
    app_lines=$(grep -v '^\s*$\|^\s*//' src/main/java/com/course/Application.java | grep -v '^\s*/\*\|^\s*\*' | wc -l)
    echo "Application: $app_lines 行有效代码 (1 个文件)"
else
    app_lines=0
    echo "Application: 文件不存在"
fi

echo
echo "=== 总计 ==="
total=$((controller_lines + service_lines + dao_lines + mapper_lines + pojo_lines + utils_lines + interceptor_lines + schedule_lines + model_lines + app_lines))
echo "后端总有效代码行数: $total 行"

echo
echo "=== 详细文件列表 ==="
find src/main/java -name "*.java" -type f | sort
