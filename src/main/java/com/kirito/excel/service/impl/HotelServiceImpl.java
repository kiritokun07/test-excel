package com.kirito.excel.service.impl;

import com.kirito.excel.domain.Hotel;
import com.kirito.excel.mapper.HotelMapper;
import com.kirito.excel.service.IHotelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * hotel 服务实现类
 * </p>
 *
 * @author kirito
 * @since 2024-06-10 21:26:21
 */
@Service
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

}
