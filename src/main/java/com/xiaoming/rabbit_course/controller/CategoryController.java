package com.xiaoming.rabbit_course.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.Category;
import com.xiaoming.rabbit_course.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @ApiOperation("新增分类")
    @Secured("ROLE_ADMIN")
    @PostMapping("/save")
    public Result<String> save(@ApiParam("分类信息") @RequestBody Category category){
        if (categoryService.save(category)){
            return Result.ok("添加分类成功");
        }
        return Result.error("添加分类失败");
    }
    @Secured("ROLE_ADMIN")
    @ApiOperation("删除分类")
    @DeleteMapping("/delete")
    public Result<String> delete(@ApiParam("分类id") @NotNull(message = "id不能为空") Long id){
        return categoryService.delete(id);
    }
    @PutMapping("/update")
    public Result<String> update(@ApiParam("要修改的分类信息") @RequestBody Category category){
        if (categoryService.updateById(category)){
            return Result.ok("修改分类成功");
        }
        return Result.error("修改分类失败");
    }
    @ApiOperation("/查询分类下的课程")
    @GetMapping("/findById")
    public Result<Category> findById(@ApiParam("分类ID") @NotNull(message = "id不能为空") Long id){
        return categoryService.findById(id);
    }
    @ApiOperation("/查询全部分类")
    @GetMapping("/findAll")
    public Result<List<Category>> findAll(){
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(lambdaQueryWrapper);
        return Result.ok("成功",list);
    }

}
