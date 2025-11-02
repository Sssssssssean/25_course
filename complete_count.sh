#!/bin/bash

echo "=== 后端完整代码行数统计（包含拦截器、数据库、测试类）==="
echo

# 精确统计函数 - 排除空行、注释行、import语句、package语句
count_effective_java_lines() {
    local file=$1
    if [ -f "$file" ]; then
        grep -v -E '^\s*$|^\s*//|^\s*/\*|\*/|^\s*\*|^\s*package|^\s*import' "$file" | wc -l | tr -d ' '
    else
        echo "0"
    fi
}

# 统计XML文件（MyBatis映射文件等）
count_effective_xml_lines() {
    local file=$1
    if [ -f "$file" ]; then
        grep -v -E '^\s*$|^\s*<!--.*-->|^\s*<!--' "$file" | wc -l | tr -d ' '
    else
        echo "0"
    fi
}

# 统计SQL文件
count_effective_sql_lines() {
    local file=$1
    if [ -f "$file" ]; then
        grep -v -E '^\s*$|^\s*--' "$file" | wc -l | tr -d ' '
    else
        echo "0"
    fi
}

# 统计配置文件
count_effective_config_lines() {
    local file=$1
    if [ -f "$file" ]; then
        grep -v -E '^\s*$|^\s*#' "$file" | wc -l | tr -d ' '
    else
        echo "0"
    fi
}

echo "=== 1. 主要业务代码 ==="

# 重新统计主要业务代码
echo
echo "Controller层:"
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
echo "Service层:"
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
echo "DAO层:"
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
echo "Mapper层:"
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
echo "POJO层:"
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
echo "Utils工具类:"
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
echo "=== 2. 拦截器 ==="
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
echo "=== 3. 定时任务 ==="
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
echo "=== 4. Model模型 ==="
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
echo "=== 5. 主启动类 ==="
app_total=0
if [ -f "src/main/java/com/course/Application.java" ]; then
    app_total=$(count_effective_java_lines "src/main/java/com/course/Application.java")
    echo "   Application.java: $app_total 行"
fi
echo "   Application小计: $app_total 行 (1 个文件)"

# 计算主要业务代码总计
main_total=$((controller_total + service_total + dao_total + mapper_total + pojo_total + utils_total + interceptor_total + schedule_total + model_total + app_total))
main_files=$((controller_files + service_files + dao_files + mapper_files + pojo_files + utils_files + interceptor_files + schedule_files + model_files + 1))

echo
echo "=== 6. 测试类 ==="
test_total=0
test_files=0
if [ -d "src/test/java" ]; then
    for file in $(find src/test/java -name "*.java" -type f); do
        if [ -f "$file" ]; then
            lines=$(count_effective_java_lines "$file")
            echo "   $(basename $file): $lines 行"
            test_total=$((test_total + lines))
            test_files=$((test_files + 1))
        fi
    done
fi
echo "   测试类小计: $test_total 行 ($test_files 个文件)"

echo
echo "=== 7. 数据库相关文件 ==="

# MyBatis XML映射文件
xml_total=0
xml_files=0
if [ -d "src/main/resources" ]; then
    echo "   MyBatis映射文件:"
    for file in $(find src/main/resources -name "*.xml" -type f); do
        if [ -f "$file" ]; then
            lines=$(count_effective_xml_lines "$file")
            echo "     $(basename $file): $lines 行"
            xml_total=$((xml_total + lines))
            xml_files=$((xml_files + 1))
        fi
    done
fi

# SQL脚本文件
sql_total=0
sql_files=0
echo "   SQL脚本文件:"
for file in $(find . -name "*.sql" -type f 2>/dev/null); do
    if [ -f "$file" ]; then
        lines=$(count_effective_sql_lines "$file")
        echo "     $(basename $file): $lines 行"
        sql_total=$((sql_total + lines))
        sql_files=$((sql_files + 1))
    fi
done

db_total=$((xml_total + sql_total))
db_files=$((xml_files + sql_files))
echo "   数据库文件小计: $db_total 行 ($db_files 个文件)"

echo
echo "=== 8. 配置文件 ==="
config_total=0
config_files=0
if [ -d "src/main/resources" ]; then
    for file in src/main/resources/*.properties src/main/resources/*.yml src/main/resources/*.yaml; do
        if [ -f "$file" ]; then
            lines=$(count_effective_config_lines "$file")
            echo "   $(basename $file): $lines 行"
            config_total=$((config_total + lines))
            config_files=$((config_files + 1))
        fi
    done
fi
echo "   配置文件小计: $config_total 行 ($config_files 个文件)"

# 计算总计
grand_total=$((main_total + test_total + db_total + config_total))
total_files=$((main_files + test_files + db_files + config_files))

echo
echo "=== 最终完整汇总 ==="
echo "┌─────────────────────┬──────────┬──────────┐"
echo "│ 模块                │ 代码行数 │ 文件数量 │"
echo "├─────────────────────┼──────────┼──────────┤"
printf "│ %-19s │ %8d │ %8d │\n" "Controller层" $controller_total $controller_files
printf "│ %-19s │ %8d │ %8d │\n" "Service层" $service_total $service_files
printf "│ %-19s │ %8d │ %8d │\n" "DAO层" $dao_total $dao_files
printf "│ %-19s │ %8d │ %8d │\n" "Mapper层" $mapper_total $mapper_files
printf "│ %-19s │ %8d │ %8d │\n" "POJO层" $pojo_total $pojo_files
printf "│ %-19s │ %8d │ %8d │\n" "Utils工具类" $utils_total $utils_files
printf "│ %-19s │ %8d │ %8d │\n" "Interceptor拦截器" $interceptor_total $interceptor_files
printf "│ %-19s │ %8d │ %8d │\n" "Schedule定时任务" $schedule_total $schedule_files
printf "│ %-19s │ %8d │ %8d │\n" "Model模型" $model_total $model_files
printf "│ %-19s │ %8d │ %8d │\n" "主启动类" $app_total 1
echo "├─────────────────────┼──────────┼──────────┤"
printf "│ %-19s │ %8d │ %8d │\n" "主要业务代码小计" $main_total $main_files
echo "├─────────────────────┼──────────┼──────────┤"
printf "│ %-19s │ %8d │ %8d │\n" "测试类" $test_total $test_files
printf "│ %-19s │ %8d │ %8d │\n" "数据库文件" $db_total $db_files
printf "│ %-19s │ %8d │ %8d │\n" "配置文件" $config_total $config_files
echo "├─────────────────────┼──────────┼──────────┤"
printf "│ %-19s │ %8d │ %8d │\n" "项目总计" $grand_total $total_files
echo "└─────────────────────┴──────────┴──────────┘"

echo
echo "=== 代码质量分析 ==="
echo "• 主要业务代码: $main_total 行 ($(echo "scale=1; $main_total * 100 / $grand_total" | bc)%)"
echo "• 测试代码: $test_total 行 ($(echo "scale=1; $test_total * 100 / $grand_total" | bc)%)"
echo "• 数据库相关: $db_total 行 ($(echo "scale=1; $db_total * 100 / $grand_total" | bc)%)"
echo "• 配置文件: $config_total 行 ($(echo "scale=1; $config_total * 100 / $grand_total" | bc)%)"

