#!/bin/bash

echo "=== 后端代码详细统计 ==="
echo

# 更精确的统计函数
count_lines_detailed() {
    local dir=$1
    local name=$2
    
    if [ -d "$dir" ]; then
        echo "--- $name ---"
        local total=0
        local file_count=0
        
        for file in $(find "$dir" -name "*.java" -type f | sort); do
            # 统计有效代码行（排除空行、单行注释、多行注释）
            local lines=$(sed '/^\s*$/d; /^\s*\/\//d; /^\s*\/\*/,/\*\//d' "$file" | wc -l | tr -d ' ')
            echo "  $(basename $file): $lines 行"
            total=$((total + lines))
            file_count=$((file_count + 1))
        done
        
        echo "  小计: $total 行 ($file_count 个文件)"
        echo
        return $total
    else
        echo "--- $name ---"
        echo "  目录不存在"
        echo
        return 0
    fi
}

# 统计各个模块
count_lines_detailed "src/main/java/com/course/controller" "Controller层"
controller_total=$?

count_lines_detailed "src/main/java/com/course/service" "Service层"
service_total=$?

count_lines_detailed "src/main/java/com/course/dao" "DAO层"
dao_total=$?

count_lines_detailed "src/main/java/com/course/mapper" "Mapper层"
mapper_total=$?

count_lines_detailed "src/main/java/com/course/pojo" "POJO层"
pojo_total=$?

count_lines_detailed "src/main/java/com/course/utils" "Utils工具类"
utils_total=$?

count_lines_detailed "src/main/java/com/course/interceptor" "Interceptor拦截器"
interceptor_total=$?

count_lines_detailed "src/main/java/com/course/schedule" "Schedule定时任务"
schedule_total=$?

count_lines_detailed "src/main/java/com/course/model" "Model模型"
model_total=$?

echo "--- 主启动类 ---"
if [ -f "src/main/java/com/course/Application.java" ]; then
    app_lines=$(sed '/^\s*$/d; /^\s*\/\//d; /^\s*\/\*/,/\*\//d' "src/main/java/com/course/Application.java" | wc -l | tr -d ' ')
    echo "  Application.java: $app_lines 行"
    echo "  小计: $app_lines 行 (1 个文件)"
else
    app_lines=0
    echo "  Application.java: 文件不存在"
    echo "  小计: 0 行 (0 个文件)"
fi
echo

# 总计
grand_total=$((controller_total + service_total + dao_total + mapper_total + pojo_total + utils_total + interceptor_total + schedule_total + model_total + app_lines))

echo "=== 汇总统计 ==="
echo "Controller层:    $controller_total 行"
echo "Service层:       $service_total 行"
echo "DAO层:           $dao_total 行"
echo "Mapper层:        $mapper_total 行"
echo "POJO层:          $pojo_total 行"
echo "Utils工具类:     $utils_total 行"
echo "Interceptor拦截器: $interceptor_total 行"
echo "Schedule定时任务: $schedule_total 行"
echo "Model模型:       $model_total 行"
echo "主启动类:        $app_lines 行"
echo "------------------------"
echo "总计:            $grand_total 行有效代码"

