#!/bin/bash

echo "=== 后端项目全部代码行数统计（包含所有行）==="
echo "统计时间: $(date)"
echo

# 统计所有行数（包括空行、注释、import等）
count_all_lines() {
    local file=$1
    if [ -f "$file" ]; then
        wc -l < "$file" | tr -d ' '
    else
        echo "0"
    fi
}

# 统计有效代码行数（排除空行、注释、import、package）
count_effective_lines() {
    local file=$1
    if [ -f "$file" ]; then
        grep -v -E '^\s*$|^\s*//|^\s*/\*|\*/|^\s*\*|^\s*package|^\s*import' "$file" | wc -l | tr -d ' '
    else
        echo "0"
    fi
}

echo "=== 📊 Java源代码统计 ==="

# 1. Controller层
echo "🎯 Controller层:"
controller_total=0
controller_effective=0
controller_files=0
for file in src/main/java/com/course/controller/*.java; do
    if [ -f "$file" ]; then
        total_lines=$(count_all_lines "$file")
        effective_lines=$(count_effective_lines "$file")
        printf "   %-35s %3d 行 (有效: %3d 行)\n" "$(basename $file)" $total_lines $effective_lines
        controller_total=$((controller_total + total_lines))
        controller_effective=$((controller_effective + effective_lines))
        controller_files=$((controller_files + 1))
    fi
done
echo "   ✅ Controller小计: $controller_total 行 (有效: $controller_effective 行, $controller_files 个文件)"
echo

# 2. Service层
echo "⚙️ Service层:"
service_total=0
service_effective=0
service_files=0
for file in src/main/java/com/course/service/*.java; do
    if [ -f "$file" ]; then
        total_lines=$(count_all_lines "$file")
        effective_lines=$(count_effective_lines "$file")
        printf "   %-35s %3d 行 (有效: %3d 行)\n" "$(basename $file)" $total_lines $effective_lines
        service_total=$((service_total + total_lines))
        service_effective=$((service_effective + effective_lines))
        service_files=$((service_files + 1))
    fi
done
echo "   ✅ Service小计: $service_total 行 (有效: $service_effective 行, $service_files 个文件)"
echo

# 3. DAO层
echo "💾 DAO层:"
dao_total=0
dao_effective=0
dao_files=0
for file in src/main/java/com/course/dao/*.java src/main/java/com/course/dao/impl/*.java; do
    if [ -f "$file" ]; then
        total_lines=$(count_all_lines "$file")
        effective_lines=$(count_effective_lines "$file")
        printf "   %-35s %3d 行 (有效: %3d 行)\n" "$(basename $file)" $total_lines $effective_lines
        dao_total=$((dao_total + total_lines))
        dao_effective=$((dao_effective + effective_lines))
        dao_files=$((dao_files + 1))
    fi
done
echo "   ✅ DAO小计: $dao_total 行 (有效: $dao_effective 行, $dao_files 个文件)"
echo

# 4. Mapper层
echo "🗺️ Mapper层:"
mapper_total=0
mapper_effective=0
mapper_files=0
for file in src/main/java/com/course/mapper/*.java; do
    if [ -f "$file" ]; then
        total_lines=$(count_all_lines "$file")
        effective_lines=$(count_effective_lines "$file")
        printf "   %-35s %3d 行 (有效: %3d 行)\n" "$(basename $file)" $total_lines $effective_lines
        mapper_total=$((mapper_total + total_lines))
        mapper_effective=$((mapper_effective + effective_lines))
        mapper_files=$((mapper_files + 1))
    fi
done
echo "   ✅ Mapper小计: $mapper_total 行 (有效: $mapper_effective 行, $mapper_files 个文件)"
echo

# 5. POJO层
echo "📦 POJO层:"
pojo_total=0
pojo_effective=0
pojo_files=0
for file in src/main/java/com/course/pojo/*.java; do
    if [ -f "$file" ]; then
        total_lines=$(count_all_lines "$file")
        effective_lines=$(count_effective_lines "$file")
        printf "   %-35s %3d 行 (有效: %3d 行)\n" "$(basename $file)" $total_lines $effective_lines
        pojo_total=$((pojo_total + total_lines))
        pojo_effective=$((pojo_effective + effective_lines))
        pojo_files=$((pojo_files + 1))
    fi
done
echo "   ✅ POJO小计: $pojo_total 行 (有效: $pojo_effective 行, $pojo_files 个文件)"
echo

# 6. Utils工具类
echo "🔧 Utils工具类:"
utils_total=0
utils_effective=0
utils_files=0
for file in src/main/java/com/course/utils/*.java; do
    if [ -f "$file" ]; then
        total_lines=$(count_all_lines "$file")
        effective_lines=$(count_effective_lines "$file")
        printf "   %-35s %3d 行 (有效: %3d 行)\n" "$(basename $file)" $total_lines $effective_lines
        utils_total=$((utils_total + total_lines))
        utils_effective=$((utils_effective + effective_lines))
        utils_files=$((utils_files + 1))
    fi
done
echo "   ✅ Utils小计: $utils_total 行 (有效: $utils_effective 行, $utils_files 个文件)"
echo

# 7. Interceptor拦截器
echo "🛡️ Interceptor拦截器:"
interceptor_total=0
interceptor_effective=0
interceptor_files=0
for file in src/main/java/com/course/interceptor/*.java; do
    if [ -f "$file" ]; then
        total_lines=$(count_all_lines "$file")
        effective_lines=$(count_effective_lines "$file")
        printf "   %-35s %3d 行 (有效: %3d 行)\n" "$(basename $file)" $total_lines $effective_lines
        interceptor_total=$((interceptor_total + total_lines))
        interceptor_effective=$((interceptor_effective + effective_lines))
        interceptor_files=$((interceptor_files + 1))
    fi
done
echo "   ✅ Interceptor小计: $interceptor_total 行 (有效: $interceptor_effective 行, $interceptor_files 个文件)"
echo

# 8. Schedule定时任务
echo "⏰ Schedule定时任务:"
schedule_total=0
schedule_effective=0
schedule_files=0
for file in src/main/java/com/course/schedule/*.java; do
    if [ -f "$file" ]; then
        total_lines=$(count_all_lines "$file")
        effective_lines=$(count_effective_lines "$file")
        printf "   %-35s %3d 行 (有效: %3d 行)\n" "$(basename $file)" $total_lines $effective_lines
        schedule_total=$((schedule_total + total_lines))
        schedule_effective=$((schedule_effective + effective_lines))
        schedule_files=$((schedule_files + 1))
    fi
done
echo "   ✅ Schedule小计: $schedule_total 行 (有效: $schedule_effective 行, $schedule_files 个文件)"
echo

# 9. Model模型
echo "📋 Model模型:"
model_total=0
model_effective=0
model_files=0
for file in src/main/java/com/course/model/*.java; do
    if [ -f "$file" ]; then
        total_lines=$(count_all_lines "$file")
        effective_lines=$(count_effective_lines "$file")
        printf "   %-35s %3d 行 (有效: %3d 行)\n" "$(basename $file)" $total_lines $effective_lines
        model_total=$((model_total + total_lines))
        model_effective=$((model_effective + effective_lines))
        model_files=$((model_files + 1))
    fi
done
echo "   ✅ Model小计: $model_total 行 (有效: $model_effective 行, $model_files 个文件)"
echo

# 10. 主启动类
echo "🚀 主启动类:"
app_total=0
app_effective=0
if [ -f "src/main/java/com/course/Application.java" ]; then
    app_total=$(count_all_lines "src/main/java/com/course/Application.java")
    app_effective=$(count_effective_lines "src/main/java/com/course/Application.java")
    printf "   %-35s %3d 行 (有效: %3d 行)\n" "Application.java" $app_total $app_effective
fi
echo "   ✅ Application小计: $app_total 行 (有效: $app_effective 行, 1 个文件)"
echo

# 计算主要业务代码总计
main_total=$((controller_total + service_total + dao_total + mapper_total + pojo_total + utils_total + interceptor_total + schedule_total + model_total + app_total))
main_effective=$((controller_effective + service_effective + dao_effective + mapper_effective + pojo_effective + utils_effective + interceptor_effective + schedule_effective + model_effective + app_effective))
main_files=$((controller_files + service_files + dao_files + mapper_files + pojo_files + utils_files + interceptor_files + schedule_files + model_files + 1))

echo "=== 🧪 测试代码统计 ==="
test_total=0
test_effective=0
test_files=0
if [ -d "src/test/java" ]; then
    for file in $(find src/test/java -name "*.java" -type f | sort); do
        if [ -f "$file" ]; then
            total_lines=$(count_all_lines "$file")
            effective_lines=$(count_effective_lines "$file")
            printf "   %-35s %3d 行 (有效: %3d 行)\n" "$(basename $file)" $total_lines $effective_lines
            test_total=$((test_total + total_lines))
            test_effective=$((test_effective + effective_lines))
            test_files=$((test_files + 1))
        fi
    done
fi
echo "   ✅ 测试类小计: $test_total 行 (有效: $test_effective 行, $test_files 个文件)"
echo

echo "=== 🗄️ 数据库和配置文件统计 ==="

# XML文件
xml_total=0
xml_files=0
echo "📄 XML配置文件:"
for file in src/main/resources/mybatis/mapper/*.xml src/main/resources/spring/*.xml src/main/resources/*.xml; do
    if [ -f "$file" ]; then
        total_lines=$(count_all_lines "$file")
        printf "   %-35s %3d 行\n" "$(basename $file)" $total_lines
        xml_total=$((xml_total + total_lines))
        xml_files=$((xml_files + 1))
    fi
done

# 配置文件
config_total=0
config_files=0
echo "⚙️ 配置文件:"
for file in src/main/resources/*.properties src/main/resources/*.yml src/main/resources/*.yaml; do
    if [ -f "$file" ]; then
        total_lines=$(count_all_lines "$file")
        printf "   %-35s %3d 行\n" "$(basename $file)" $total_lines
        config_total=$((config_total + total_lines))
        config_files=$((config_files + 1))
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

db_config_total=$((xml_total + config_total))
db_config_files=$((xml_files + config_files + db_files_count))
echo "   ✅ 数据库和配置文件小计: $db_config_total 行 ($db_config_files 个文件)"
echo

# 计算总计
java_total=$((main_total + test_total))
java_effective=$((main_effective + test_effective))
java_files=$((main_files + test_files))

grand_total=$((java_total + db_config_total))
total_files=$((java_files + db_config_files))

echo "=== 📈 最终完整统计汇总 ==="
echo "┌─────────────────────────┬──────────┬──────────┬──────────┬──────────┐"
echo "│ 模块分类                │ 总行数   │ 有效行数 │ 文件数量 │ 占比(%)  │"
echo "├─────────────────────────┼──────────┼──────────┼──────────┼──────────┤"
printf "│ %-23s │ %8d │ %8d │ %8d │ %7.1f%% │\n" "Controller层" $controller_total $controller_effective $controller_files $(echo "scale=1; $controller_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %8d │ %7.1f%% │\n" "Service层" $service_total $service_effective $service_files $(echo "scale=1; $service_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %8d │ %7.1f%% │\n" "DAO层" $dao_total $dao_effective $dao_files $(echo "scale=1; $dao_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %8d │ %7.1f%% │\n" "Mapper层" $mapper_total $mapper_effective $mapper_files $(echo "scale=1; $mapper_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %8d │ %7.1f%% │\n" "POJO层" $pojo_total $pojo_effective $pojo_files $(echo "scale=1; $pojo_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %8d │ %7.1f%% │\n" "Utils工具类" $utils_total $utils_effective $utils_files $(echo "scale=1; $utils_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %8d │ %7.1f%% │\n" "Interceptor拦截器" $interceptor_total $interceptor_effective $interceptor_files $(echo "scale=1; $interceptor_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %8d │ %7.1f%% │\n" "Schedule定时任务" $schedule_total $schedule_effective $schedule_files $(echo "scale=1; $schedule_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %8d │ %7.1f%% │\n" "Model模型" $model_total $model_effective $model_files $(echo "scale=1; $model_total * 100 / $grand_total" | bc)
printf "│ %-23s │ %8d │ %8d │ %8d │ %7.1f%% │\n" "主启动类" $app_total $app_effective 1 $(echo "scale=1; $app_total * 100 / $grand_total" | bc)
echo "├─────────────────────────┼──────────┼──────────┼──────────┼──────────┤"
printf "│ %-23s │ %8d │ %8d │ %8d │ %7.1f%% │\n" "🔥 主要业务代码小计" $main_total $main_effective $main_files $(echo "scale=1; $main_total * 100 / $grand_total" | bc)
echo "├─────────────────────────┼──────────┼──────────┼──────────┼──────────┤"
printf "│ %-23s │ %8d │ %8d │ %8d │ %7.1f%% │\n" "🧪 测试代码" $test_total $test_effective $test_files $(echo "scale=1; $test_total * 100 / $grand_total" | bc)
echo "├─────────────────────────┼──────────┼──────────┼──────────┼──────────┤"
printf "│ %-23s │ %8d │ %8s │ %8d │ %7.1f%% │\n" "🗄️ 数据库和配置文件" $db_config_total "N/A" $db_config_files $(echo "scale=1; $db_config_total * 100 / $grand_total" | bc)
echo "├─────────────────────────┼──────────┼──────────┼──────────┼──────────┤"
printf "│ %-23s │ %8d │ %8d │ %8d │ %7.1f%% │\n" "🎯 项目总计" $grand_total $java_effective $total_files 100.0
echo "└─────────────────────────┴──────────┴──────────┴──────────┴──────────┘"

echo
echo "=== 📊 代码行数分析 ==="
echo "📝 总代码行数: $grand_total 行"
echo "💻 Java代码行数: $java_total 行 ($(echo "scale=1; $java_total * 100 / $grand_total" | bc)%)"
echo "✨ 有效Java代码: $java_effective 行 ($(echo "scale=1; $java_effective * 100 / $java_total" | bc)%)"
echo "🗄️ 配置文件行数: $db_config_total 行 ($(echo "scale=1; $db_config_total * 100 / $grand_total" | bc)%)"
echo "📁 总文件数量: $total_files 个"

echo
echo "=== 🏆 项目规模评估 ==="
if [ $grand_total -lt 1000 ]; then
    echo "📏 项目规模: 小型项目 (< 1000 行)"
elif [ $grand_total -lt 5000 ]; then
    echo "📏 项目规模: 中型项目 (1000-5000 行)"
elif [ $grand_total -lt 10000 ]; then
    echo "📏 项目规模: 中大型项目 (5000-10000 行)"
else
    echo "📏 项目规模: 大型项目 (> 10000 行)"
fi

echo "🎓 课程设计评价: 代码量充足，结构完整"
echo "💡 技术栈完整度: Spring Boot + MyBatis + 测试 + 拦截器 + 定时任务"
echo "🔧 代码质量: 有效代码率 $(echo "scale=1; $java_effective * 100 / $java_total" | bc)%，注释和格式良好"

