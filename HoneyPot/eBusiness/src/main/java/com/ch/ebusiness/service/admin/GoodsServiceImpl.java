package com.ch.ebusiness.service.admin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ch.ebusiness.controller.before.Write;
import com.ch.ebusiness.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.ch.ebusiness.entity.Goods;
import com.ch.ebusiness.repository.admin.GoodsRepository;
import com.ch.ebusiness.util.MyUtil;

@Service
public class GoodsServiceImpl implements GoodsService {
    int counter = 1;
    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public String selectAllGoodsByPage(Model model, int currentPage, String act) {
        //共多少个商品
        int totalCount = goodsRepository.selectAllGoods();
        //计算共多少页
        int pageSize = 5;
        int totalPage = (int) Math.ceil(totalCount * 1.0 / pageSize);
        List<Goods> typeByPage = goodsRepository.selectAllGoodsByPage((currentPage - 1) * pageSize, pageSize);
        model.addAttribute("allGoods", typeByPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("act", act);
        return "admin/selectGoods";
    }

    @Override
    public String addGoods(Goods goods, HttpServletRequest request, String act) throws IllegalStateException, IOException {
        MultipartFile myfile = goods.getFileName();
        //如果选择了上传文件，将文件上传到指定的目录images
        if (!myfile.isEmpty()) {
            //上传文件路径（生产环境）
//            String path = "/honeypot/files";
//			String path = request.getServletContext().getRealPath("/images/");
//			String path = "/dairy/";
            String path = "D:\\J2EE_project\\eBusiness\\src\\main\\resources\\static\\apk";
            System.out.println("path:" + path);

            Write write = new Write();
            //记录ip
            String ip = IpUtil.getIpAddr(request);//获取ip
//		System.out.println(ip);
            //写入记录
            write.WriteIntoMyText("ip地址:["+ip+"]",3);

            write.WriteIntoMyText("添加上传的商品信息"+(counter++)+":", 3);
			write.WriteIntoMyText("商品名称：" + goods.getGname(), 3);
			write.WriteIntoMyText("上传路径：" + path, 3);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String time = String.valueOf(df.format(new Date()));
            write.WriteIntoMyText("添加时间:" + time, 3);
            write.WriteIntoMyText("--------------------------", 3);


            //获得上传文件原名
            //上传文件路径（开发环境）
//			String path = "C:\\workspace-spring-tool-suite-4-4.1.1.RELEASE\\eBusiness\\src\\main\\resources\\static\\images";
            //获得上传文件原名
            String fileName = myfile.getOriginalFilename();
            //对文件重命名
            String fileNewName = MyUtil.getNewFileName(fileName);

            File filePath = new File(path + File.separator + fileNewName + ip);
            //如果文件目录不存在，创建目录
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件中
            myfile.transferTo(filePath);
            //将重命名后的图片名存到goods对象中，添加时使用
            goods.setGpicture(fileNewName);
        }
        if ("add".equals(act)) {
            int n = goodsRepository.addGoods(goods);
            if (n > 0)//成功
            {
                return "redirect:/goods/selectAllGoodsByPage?currentPage=1&act=select";
            }
            //失败
            return "admin/addGoods";
        } else {//修改
            int n = goodsRepository.updateGoods(goods);
            if (n > 0)//成功
                return "redirect:/goods/selectAllGoodsByPage?currentPage=1&act=updateSelect";
            //失败
            return "admin/UpdateAGoods";
        }
    }

    @Override
    public String toAddGoods(Goods goods, Model model) {
        model.addAttribute("goodsType", goodsRepository.selectAllGoodsType());
        return "admin/addGoods";
    }

    @Override
    public String detail(Model model, Integer id, String act) {
        model.addAttribute("goods", goodsRepository.selectAGoods(id));
        if ("detail".equals(act))
            return "admin/detail";
        else {
            model.addAttribute("goodsType", goodsRepository.selectAllGoodsType());
            return "admin/updateAGoods";
        }
    }

    @Override
    public String delete(Integer id) {
        if (goodsRepository.selectCartGoods(id).size() > 0
                || goodsRepository.selectFocusGoods(id).size() > 0
                || goodsRepository.selectOrderGoods(id).size() > 0)
            return "no";
        else {
            goodsRepository.deleteAGoods(id);
            return "/goods/selectAllGoodsByPage?currentPage=1&act=deleteSelect";
        }
    }

}
