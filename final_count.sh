#!/bin/bash

echo "=== 后端Java代码行数统计（最终版本）==="
echo

# 精确统计函数 - 排除空行、注释行、import语句、package语句
count_effective_java_lines() {
    local file=$1
    # 使用更精确的过滤规则
    grep -v -E '^\s*$|^\s*//|^\s*/\*|\*/|^\s*\*|^\s*package|^\s*import' "$file" | wc -l | tr -d ' '
}

# 统计各个模块
echo "=== 详细统计 ==="

echo
echo "1. Controller层:"
controller_total=0
controller_files=0
for file in src/main/java/com/course/controller/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        echo "   $(basename $file): $lines 行"
        controller_total=$((controller_total + lines))
        controller_files=$((controller_files + 1))
    fi
done
echo "   Controller小计: $controller_total 行 ($controller_files 个文件)"

echo
echo "2. Service层:"
service_total=0
service_files=0
for file in src/main/java/com/course/service/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        echo "   $(basename $file): $lines 行"
        service_total=$((service_total + lines))
        service_files=$((service_files + 1))
    fi
done
echo "   Service小计: $service_total 行 ($service_files 个文件)"

echo
echo "3. DAO层:"
dao_total=0
dao_files=0
for file in src/main/java/com/course/dao/*.java src/main/java/com/course/dao/impl/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        echo "   $(basename $file): $lines 行"
        dao_total=$((dao_total + lines))
        dao_files=$((dao_files + 1))
    fi
done
echo "   DAO小计: $dao_total 行 ($dao_files 个文件)"

echo
echo "4. Mapper层:"
mapper_total=0
mapper_files=0
for file in src/main/java/com/course/mapper/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        echo "   $(basename $file): $lines 行"
        mapper_total=$((mapper_total + lines))
        mapper_files=$((mapper_files + 1))
    fi
done
echo "   Mapper小计: $mapper_total 行 ($mapper_files 个文件)"

echo
echo "5. POJO层:"
pojo_total=0
pojo_files=0
for file in src/main/java/com/course/pojo/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        echo "   $(basename $file): $lines 行"
        pojo_total=$((pojo_total + lines))
        pojo_files=$((pojo_files + 1))
    fi
done
echo "   POJO小计: $pojo_total 行 ($pojo_files 个文件)"

echo
echo "6. Utils工具类:"
utils_total=0
utils_files=0
for file in src/main/java/com/course/utils/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        echo "   $(basename $file): $lines 行"
        utils_total=$((utils_total + lines))
        utils_files=$((utils_files + 1))
    fi
done
echo "   Utils小计: $utils_total 行 ($utils_files 个文件)"

echo
echo "7. Interceptor拦截器:"
interceptor_total=0
interceptor_files=0
for file in src/main/java/com/course/interceptor/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        echo "   $(basename $file): $lines 行"
        interceptor_total=$((interceptor_total + lines))
        interceptor_files=$((interceptor_files + 1))
    fi
done
echo "   Interceptor小计: $interceptor_total 行 ($interceptor_files 个文件)"

echo
echo "8. Schedule定时任务:"
schedule_total=0
schedule_files=0
for file in src/main/java/com/course/schedule/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        echo "   $(basename $file): $lines 行"
        schedule_total=$((schedule_total + lines))
        schedule_files=$((schedule_files + 1))
    fi
done
echo "   Schedule小计: $schedule_total 行 ($schedule_files 个文件)"

echo
echo "9. Model模型:"
model_total=0
model_files=0
for file in src/main/java/com/course/model/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        echo "   $(basename $file): $lines 行"
        model_total=$((model_total + lines))
        model_files=$((model_files + 1))
    fi
done
echo "   Model小计: $model_total 行 ($model_files 个文件)"

echo
echo "10. 主启动类:"
app_total=0
if [ -f "src/main/java/com/course/Application.java" ]; then
    app_total=$(count_effective_java_lines "src/main/java/com/course/Application.java")
    echo "   Application.java: $app_total 行"
fi
echo "   Application小计: $app_total 行 (1 个文件)"

# 计算总计
grand_total=$((controller_total + service_total + dao_total + mapper_total + pojo_total + utils_total + interceptor_total + schedule_total + model_total + app_total))
total_files=$((controller_files + service_files + dao_files + mapper_files + pojo_files + utils_files + interceptor_files + schedule_files + model_files + 1))

echo
echo "=== 最终汇总 ==="
echo "┌─────────────────┬──────────┬──────────┐"
echo "│ 模块            │ 代码行数 │ 文件数量 │"
echo "├─────────────────┼──────────┼──────────┤"
printf "│ %-15s │ %8d │ %8d │\n" "Controller层" $controller_total $controller_files
printf "│ %-15s │ %8d │ %8d │\n" "Service层" $service_total $service_files
printf "│ %-15s │ %8d │ %8d │\n" "DAO层" $dao_total $dao_files
printf "│ %-15s │ %8d │ %8d │\n" "Mapper层" $mapper_total $mapper_files
printf "│ %-15s │ %8d │ %8d │\n" "POJO层" $pojo_total $pojo_files
printf "│ %-15s │ %8d │ %8d │\n" "Utils工具类" $utils_total $utils_files
printf "│ %-15s │ %8d │ %8d │\n" "Interceptor拦截器" $interceptor_total $interceptor_files
printf "│ %-15s │ %8d │ %8d │\n" "Schedule定时任务" $schedule_total $schedule_files
printf "│ %-15s │ %8d │ %8d │\n" "Model模型" $model_total $model_files
printf "│ %-15s │ %8d │ %8d │\n" "主启动类" $app_total 1
echo "├─────────────────┼──────────┼──────────┤"
printf "│ %-15s │ %8d │ %8d │\n" "总计" $grand_total $total_files
echo "└─────────────────┴──────────┴──────────┘"

