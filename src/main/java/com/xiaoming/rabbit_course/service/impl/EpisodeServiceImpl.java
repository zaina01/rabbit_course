package com.xiaoming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoming.rabbit_course.entity.Episode;
import com.xiaoming.rabbit_course.mapper.EpisodeMapper;
import com.xiaoming.rabbit_course.service.EpisodeService;
import org.springframework.stereotype.Service;

@Service
public class EpisodeServiceImpl extends ServiceImpl<EpisodeMapper, Episode> implements EpisodeService {
}
