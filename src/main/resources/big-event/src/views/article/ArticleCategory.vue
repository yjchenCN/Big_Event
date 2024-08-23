<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

const categorys = ref([
    {
        "id": 3,
        "categoryName": "美食",
        "categoryAlias": "my",
        "createTime": "2023-09-02 12:06:59",
        "updateTime": "2023-09-02 12:06:59"
    },
    {
        "id": 4,
        "categoryName": "娱乐",
        "categoryAlias": "yl",
        "createTime": "2023-09-02 12:08:16",
        "updateTime": "2023-09-02 12:08:16"
    },
    {
        "id": 5,
        "categoryName": "军事",
        "categoryAlias": "js",
        "createTime": "2023-09-02 12:08:33",
        "updateTime": "2023-09-02 12:08:33"
    }
])

//声明异步函数
import { articleCateListService, articleCateAddService, articleCateUpdateService, articleCateDeleteService} from '@/api/article.js'
const articleCategoryList = async () => {
    let result = await articleCateListService();
    categorys.value = result.data;
}
articleCategoryList();


//控制添加分类弹窗
const dialogVisible = ref(false)  //弹窗默认不显示
//添加分类数据模型
const categoryModel = ref({
    categoryName: '',
    categoryAlias: ''
})
//添加分类表单校验
const rules = {
    categoryName: [
        { required: true, message: '请输入分类名称', trigger: 'blur' },
    ],
    categoryAlias: [
        { required: true, message: '请输入分类别名', trigger: 'blur' },
    ]
}

//调用接口添加表单
const addCategory = async () => {
    let result = await articleCateAddService(categoryModel.value);
    ElMessage.success(result.message ? result.message : '添加成功')
    //调用获取文章分类，也就是刷新，让新添加的文章显示出来
    articleCategoryList();
    //隐藏窗口
    dialogVisible.value = false;
}

//定义变量，控制标题的展示
const title = ref('')

//展示编辑弹唱
const showDialog = (row) => {
    dialogVisible.value = true;
    title.value = '编辑分类';
    categoryModel.value.categoryName = row.categoryName;  //把当前行的数据赋值给模型数据
    categoryModel.value.categoryAlias = row.categoryAlias;
    //扩展id属性，将来要传递给后台，完成分类的修改
    categoryModel.value.id = row.id;
}

//编辑分类
const updateCategory = async () => {
    let result = await articleCateUpdateService(categoryModel.value);
    ElMessage.success(result.message ? result.message : '修改成功')
    //调用获取文章分类，也就是刷新，让新添加的文章显示出来
    articleCategoryList();
    //隐藏窗口
    dialogVisible.value = false;
}

//清空模型数据，用于点击添加分类弹窗时清空数据
const clearModel = () => {
    categoryModel.value.categoryName = '';
    categoryModel.value.categoryAlias = '';
}

//删除分类
import {ElMessageBox} from 'element-plus'
const deleteCategory = async (row) => {
    //提示用户，用element-plus的提示框
    ElMessageBox.confirm(
        '确认要删除分类吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            let result = await articleCateDeleteService(row.id);
            ElMessage({
                type: 'success',
                message: '删除成功',
            })
            //刷新列表
            articleCategoryList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '用户取消了删除',
            })
        })
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>文章分类</span>
                <div class="extra">
                    <el-button type="primary"
                        @click="dialogVisible = true; title = '添加分类'; clearModel()">添加分类</el-button>
                </div>
            </div>
        </template>
        <el-table :data="categorys" style="width: 100%">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="分类名称" prop="categoryName"></el-table-column>
            <el-table-column label="分类别名" prop="categoryAlias"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }"> <!--"{ row }"代表当前行的数据-->
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteCategory(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <!-- 添加分类弹窗 -->
        <el-dialog v-model="dialogVisible" :title="title" width="30%">
            <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="分类名称" prop="categoryName">
                    <el-input v-model="categoryModel.categoryName" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="分类别名" prop="categoryAlias">
                    <el-input v-model="categoryModel.categoryAlias" minlength="1" maxlength="15"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="title == '添加分类' ? addCategory() : updateCategory()"> 确认
                    </el-button>
                </span>
            </template>
        </el-dialog>

    </el-card>
</template>

<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
</style>