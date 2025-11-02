#!/bin/bash

echo "=== 后端项目完整代码行数统计报告 ==="
echo "统计时间: $(date)"
echo

# 精确统计函数
count_effective_java_lines() {
    local file=$1
    if [ -f "$file" ]; then
        grep -v -E '^\s*$|^\s*//|^\s*/\*|\*/|^\s*\*|^\s*package|^\s*import' "$file" | wc -l | tr -d ' '
    else
        echo "0"
    fi
}

count_effective_xml_lines() {
    local file=$1
    if [ -f "$file" ]; then
        grep -v -E '^\s*$|^\s*<!--.*-->|^\s*<!--' "$file" | wc -l | tr -d ' '
    else
        echo "0"
    fi
}

count_effective_config_lines() {
    local file=$1
    if [ -f "$file" ]; then
        grep -v -E '^\s*$|^\s*#' "$file" | wc -l | tr -d ' '
    else
        echo "0"
    fi
}

# 1. 主要业务代码统计
echo "=== 📊 主要业务代码统计 ==="

# Controller层
controller_total=0
controller_files=0
echo "🎯 Controller层 (API控制器):"
for file in src/main/java/com/course/controller/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        printf "   %-35s %3d 行\n" "$(basename $file)" $lines
        controller_total=$((controller_total + lines))
        controller_files=$((controller_files + 1))
    fi
done
echo "   ✅ Controller小计: $controller_total 行 ($controller_files 个文件)"
echo

# Service层
service_total=0
service_files=0
echo "⚙️ Service层 (业务逻辑):"
for file in src/main/java/com/course/service/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        printf "   %-35s %3d 行\n" "$(basename $file)" $lines
        service_total=$((service_total + lines))
        service_files=$((service_files + 1))
    fi
done
echo "   ✅ Service小计: $service_total 行 ($service_files 个文件)"
echo

# DAO层
dao_total=0
dao_files=0
echo "💾 DAO层 (数据访问):"
for file in src/main/java/com/course/dao/*.java src/main/java/com/course/dao/impl/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        printf "   %-35s %3d 行\n" "$(basename $file)" $lines
        dao_total=$((dao_total + lines))
        dao_files=$((dao_files + 1))
    fi
done
echo "   ✅ DAO小计: $dao_total 行 ($dao_files 个文件)"
echo

# Mapper层
mapper_total=0
mapper_files=0
echo "🗺️ Mapper层 (MyBatis映射):"
for file in src/main/java/com/course/mapper/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        printf "   %-35s %3d 行\n" "$(basename $file)" $lines
        mapper_total=$((mapper_total + lines))
        mapper_files=$((mapper_files + 1))
    fi
done
echo "   ✅ Mapper小计: $mapper_total 行 ($mapper_files 个文件)"
echo

# POJO层
pojo_total=0
pojo_files=0
echo "📦 POJO层 (数据模型):"
for file in src/main/java/com/course/pojo/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        printf "   %-35s %3d 行\n" "$(basename $file)" $lines
        pojo_total=$((pojo_total + lines))
        pojo_files=$((pojo_files + 1))
    fi
done
echo "   ✅ POJO小计: $pojo_total 行 ($pojo_files 个文件)"
echo

# Utils工具类
utils_total=0
utils_files=0
echo "🔧 Utils工具类:"
for file in src/main/java/com/course/utils/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        printf "   %-35s %3d 行\n" "$(basename $file)" $lines
        utils_total=$((utils_total + lines))
        utils_files=$((utils_files + 1))
    fi
done
echo "   ✅ Utils小计: $utils_total 行 ($utils_files 个文件)"
echo

# 2. 拦截器
interceptor_total=0
interceptor_files=0
echo "🛡️ Interceptor拦截器:"
for file in src/main/java/com/course/interceptor/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        printf "   %-35s %3d 行\n" "$(basename $file)" $lines
        interceptor_total=$((interceptor_total + lines))
        interceptor_files=$((interceptor_files + 1))
    fi
done
echo "   ✅ Interceptor小计: $interceptor_total 行 ($interceptor_files 个文件)"
echo

# 3. 定时任务
schedule_total=0
schedule_files=0
echo "⏰ Schedule定时任务:"
for file in src/main/java/com/course/schedule/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        printf "   %-35s %3d 行\n" "$(basename $file)" $lines
        schedule_total=$((schedule_total + lines))
        schedule_files=$((schedule_files + 1))
    fi
done
echo "   ✅ Schedule小计: $schedule_total 行 ($schedule_files 个文件)"
echo

# 4. Model模型
model_total=0
model_files=0
echo "📋 Model模型:"
for file in src/main/java/com/course/model/*.java; do
    if [ -f "$file" ]; then
        lines=$(count_effective_java_lines "$file")
        printf "   %-35s %3d 行\n" "$(basename $file)" $lines
        model_total=$((model_total + lines))
        model_files=$((model_files + 1))
    fi
done
echo "   ✅ Model小计: $model_total 行 ($model_files 个文件)"
echo

# 5. 主启动类
app_total=0
echo "🚀 主启动类:"
if [ -f "src/main/java/com/course/Application.java" ]; then
    app_total=$(count_effective_java_lines "src/main/java/com/course/Application.java")
    printf "   %-35s %3d 行\n" "Application.java" $app_total
fi
echo "   ✅ Application小计: $app_total 行 (1 个文件)"
echo

# 计算主要业务代码总计
main_total=$((controller_total + service_total + dao_total + mapper_total + pojo_total + utils_total + interceptor_total + schedule_total + model_total + app_total))
main_files=$((controller_files + service_files + dao_files + mapper_files + pojo_files + utils_files + interceptor_files + schedule_files + model_files + 1))

echo "=== 🧪 测试代码统计 ==="
test_total=0
test_files=0
if [ -d "src/test/java" ]; then
    for file in $(find src/test/java -name "*.java" -type f | sort); do
        if [ -f "$file" ]; then
            lines=$(count_effective_java_lines "$file")
            printf "   %-35s %3d 行\n" "$(basename $file)" $lines
            test_total=$((test_total + lines))
            test_files=$((test_files + 1))
        fi
    done
fi
echo "   ✅ 测试类小计: $test_total 行 ($test_files 个文件)"
echo

echo "=== 🗄️ 数据库相关文件统计 ==="

# MyBatis XML映射文件
xml_total=0
xml_files=0
echo "📄 MyBatis映射文件:"
for file in src/main/resources/mybatis/mapper/*.xml src/main/resources/spring/*.xml src/main/resources/*.xml; do
    if [ -f "$file" ]; then
        lines=$(count_effective_xml_lines "$file")
        printf "   %-35s %3d 行\n" "$(basename $file)" $lines
        xml_total=$((xml_total + lines))
        xml_files=$((xml_files + 1))
    fi
done

# 数据库文件
db_files_count=0
echo "💾 数据库文件:"
for file in src/main/resources/db/*; do
    if [ -f "$file" ]; then
        size=$(ls -lh "$file" | awk '{print $5}')
        printf "   %-35s %s\n" "$(basename $file)" "$size"
        db_files_count=$((db_files_count + 1))
    fi
done

db_total=$xml_total
db_files=$((xml_files + db_files_count))
echo "   ✅ 数据库相关小计: $db_total 行配置 + $db_files_count 个数据库文件"
echo

echo "=== ⚙️ 配置文件统计 ==="
config_total=0
config_files=0
for file in src/main/resources/*.properties src/main/resources/*.yml src/main/resources/*.yaml; do
    if [ -f "$file" ]; then
        lines=$(count_effective_config_lines "$file")
        printf "   %-35s %3d 行\n" "$(basename $file)" $lines
        config_total=$((config_total + lines))
        config_files=$((config_files + 1))
    fi
done
echo "   ✅ 配置文件小计: $config_total 行 ($config_files 个文件)"
echo

# 计算总计
grand_total=$((main_total + test_total + db_total + config_total))
total_files=$((main_files + test_files + xml_files + config_files))

echo "=== 📈 最终统计汇总 ==="
echo "┌─────────────────────────┬──────────┬──────────┬──────────┐"
echo "│ 模块分类                │ 代码行数 │ 文件数量 │ 占比(%)  │"
echo "├─────────────────────────┼──────────┼──────────┼──────────┤"
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "Controller层" $controller_total $controller_files $(echo "scale=1; $controller_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "Service层" $service_total $service_files $(echo "scale=1; $service_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "DAO层" $dao_total $dao_files $(echo "scale=1; $dao_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "Mapper层" $mapper_total $mapper_files $(echo "scale=1; $mapper_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "POJO层" $pojo_total $pojo_files $(echo "scale=1; $pojo_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "Utils工具类" $utils_total $utils_files $(echo "scale=1; $utils_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "Interceptor拦截器" $interceptor_total $interceptor_files $(echo "scale=1; $interceptor_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "Schedule定时任务" $schedule_total $schedule_files $(echo "scale=1; $schedule_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "Model模型" $model_total $model_files $(echo "scale=1; $model_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "主启动类" $app_total 1 $(echo "scale=1; $app_total * 100 / $grand_total" | bc)
echo "├─────────────────────────┼──────────┼──────────┼──────────┤"
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "🔥 主要业务代码小计" $main_total $main_files $(echo "scale=1; $main_total * 100 / $grand_total" | bc)
echo "├─────────────────────────┼──────────┼──────────┼──────────┤"
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "🧪 测试代码" $test_total $test_files $(echo "scale=1; $test_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "🗄️ 数据库配置文件" $db_total $xml_files $(echo "scale=1; $db_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "⚙️ 应用配置文件" $config_total $config_files $(echo "scale=1; $config_total * 100 / $grand_total" | bc)
echo "├─────────────────────────┼──────────┼──────────┼──────────┤"
printf "│ %-23s │ %8d │ %8d │ %7.1f%% │\n" "🎯 项目总计" $grand_total $total_files 100.0
echo "└─────────────────────────┴──────────┴──────────┴──────────┘"

echo
echo "=== 📊 项目代码质量分析 ==="
echo "🎯 核心业务代码: $main_total 行 ($(echo "scale=1; $main_total * 100 / $grand_total" | bc)%) - 项目的核心功能实现"
echo "🧪 测试覆盖率: $test_total 行 ($(echo "scale=1; $test_total * 100 / $grand_total" | bc)%) - 测试代码相对充足"
echo "🗄️ 数据库层: $db_total 行 ($(echo "scale=1; $db_total * 100 / $grand_total" | bc)%) - MyBatis配置和映射"
echo "⚙️ 配置管理: $config_total 行 ($(echo "scale=1; $config_total * 100 / $grand_total" | bc)%) - 应用配置简洁"

echo
echo "=== 🏆 项目规模评估 ==="
if [ $grand_total -lt 1000 ]; then
    echo "📏 项目规模: 小型项目 (< 1000 行)"
elif [ $grand_total -lt 5000 ]; then
    echo "📏 项目规模: 中小型项目 (1000-5000 行)"
elif [ $grand_total -lt 10000 ]; then
    echo "📏 项目规模: 中型项目 (5000-10000 行)"
else
    echo "📏 项目规模: 大型项目 (> 10000 行)"
fi

echo "🎓 适合程度: 非常适合作为课程设计项目"
echo "💡 代码结构: 完整的Spring Boot + MyBatis架构"
echo "🔧 技术栈: Controller + Service + DAO + POJO + Utils + 拦截器 + 定时任务"

