package com.xiaoming.rabbit_course.controller;

import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.Episode;
import com.xiaoming.rabbit_course.service.EpisodeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/episode")
public class EpisodeController {
    @Resource
    private EpisodeService episodeService;

    @Secured("ROLE_ADMIN")
    @ApiOperation("添加课程片段 需要ROLE_ADMIN权限")
    @PostMapping
    public Result<String> save(@RequestBody @ApiParam(value = "添加课程片段") Episode episode){
        if (episodeService.save(episode)){
            return Result.ok("添加成功");
        }
        return Result.error("添加失败");
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("删除课程片段 需要ROLE_ADMIN权限")
    @DeleteMapping("/{id}")
    public Result<String> delete(@ApiParam(value = "要删除的id",example = "0") @NotNull(message = "id不能为空") @PathVariable Long id){
        if (episodeService.removeById(id)){
            return Result.ok("删除成功");
        }
        return Result.error("删除失败");
    }
    @Secured("ROLE_ADMIN")
    @ApiOperation("修改除课程片段 需要ROLE_ADMIN权限")
    @PutMapping
    public Result<String> update(@ApiParam(value = "要修改的数据") @RequestBody Episode episode){
        if (episodeService.updateById(episode)){
            return Result.ok("修改成功");
        }
        return Result.error("修改失败");
    }
//    @ApiOperation("查询除课程片段")
//    @GetMapping("/{id}")
//    public Result<Episode> findById(@ApiParam(value = "要查询的id",example = "0") @NotNull(message = "id不能为空") @PathVariable Long id){
//        Episode episode = episodeService.getById(id);
//        return Result.ok("查询成功",episode);
//    }

//    @ApiOperation("查询课程下的所有片段")
//    @GetMapping
//    public Result<List<Episode>> findAll(){
//        List<Episode> episodes = episodeService.list();
//        return Result.ok("查询成功",episodes);
//    }


}
