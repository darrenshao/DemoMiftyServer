-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Jun 04, 2016 at 08:31 AM
-- Server version: 5.5.42
-- PHP Version: 5.6.10

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_twohalf`
--
CREATE DATABASE IF NOT EXISTS `db_twohalf` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `db_twohalf`;

-- --------------------------------------------------------

--
-- Table structure for table `t_city`
--

DROP TABLE IF EXISTS `t_city`;
CREATE TABLE IF NOT EXISTS `t_city` (
  `id` int(10) unsigned NOT NULL COMMENT '城市ID',
  `name` varchar(16) DEFAULT NULL COMMENT '城市名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市信息表';

-- --------------------------------------------------------

--
-- Table structure for table `t_feedback`
--

DROP TABLE IF EXISTS `t_feedback`;
CREATE TABLE IF NOT EXISTS `t_feedback` (
  `id` int(10) unsigned NOT NULL COMMENT '反馈ID',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `user_mobile` varchar(11) DEFAULT NULL COMMENT '用户手机',
  `feed_content` text COMMENT '反馈内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_answered` tinyint(4) DEFAULT NULL COMMENT '是否回复：0-未回复；1-已回复；',
  `answer_back` text COMMENT '回复内容',
  `answer_time` datetime DEFAULT NULL COMMENT '回复时间',
  `feed_type` tinyint(4) DEFAULT NULL COMMENT '反馈类型',
  `title` varchar(64) DEFAULT NULL COMMENT '反馈标题'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='反馈信息表';

-- --------------------------------------------------------

--
-- Table structure for table `t_order`
--

DROP TABLE IF EXISTS `t_order`;
CREATE TABLE IF NOT EXISTS `t_order` (
  `order_id` varchar(32) DEFAULT NULL COMMENT '订单ID',
  `order_name` varchar(128) DEFAULT NULL COMMENT '订单名称',
  `product_id` int(10) unsigned DEFAULT NULL COMMENT '产品ID',
  `product_name` varchar(64) DEFAULT NULL COMMENT '产品名称',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '订单状态：0-待确认；1-已确认；2-已作废',
  `pay_status` tinyint(4) DEFAULT NULL COMMENT '支付状态：0-待付款；1-已付款；2-付款失败',
  `payer_id` int(10) unsigned DEFAULT NULL COMMENT '支付人ID',
  `payer_name` varchar(64) DEFAULT NULL COMMENT '支付人',
  `pay_way` tinyint(4) DEFAULT NULL COMMENT '支付方式：1-微信；',
  `pay_3rd_seqid` varchar(64) DEFAULT NULL COMMENT '第三方支付流水号',
  `amount` decimal(11,2) DEFAULT NULL COMMENT '订单金额',
  `verify_status` tinyint(4) DEFAULT NULL COMMENT '确认状态：0-未确认；1-已确认',
  `verify_time` datetime DEFAULT NULL COMMENT '确认时间',
  `operator_id` int(10) unsigned DEFAULT NULL COMMENT '操作员ID',
  `operator_name` varchar(64) DEFAULT NULL COMMENT '操作员名称',
  `id` int(10) unsigned NOT NULL COMMENT '内部id',
  `is_signed` tinyint(4) DEFAULT NULL COMMENT '是否签到',
  `sign_time` datetime DEFAULT NULL COMMENT '签到时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息表';

-- --------------------------------------------------------

--
-- Table structure for table `t_product`
--

DROP TABLE IF EXISTS `t_product`;
CREATE TABLE IF NOT EXISTS `t_product` (
  `id` int(10) unsigned NOT NULL COMMENT '产品ID',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `title` varchar(64) DEFAULT NULL COMMENT '标题',
  `short_intro` varchar(128) DEFAULT NULL COMMENT '简要信息',
  `long_intro` text COMMENT '详细信息',
  `address` varchar(128) DEFAULT NULL COMMENT '地址',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `ticket_price` decimal(11,2) DEFAULT NULL COMMENT '门票价格',
  `sponsor_id` int(10) unsigned DEFAULT NULL COMMENT '主办方ID',
  `sponsor_name` varchar(64) DEFAULT NULL COMMENT '主办方名称',
  `rating` int(11) DEFAULT NULL COMMENT '等级',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态：1-草稿；2-已发布；3-已过期',
  `is_ recommend` tinyint(4) DEFAULT NULL COMMENT '是否推荐：0-不推荐；1-推荐；',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `operator_id` int(10) unsigned DEFAULT NULL COMMENT '操作员ID',
  `operator_name` varchar(64) DEFAULT NULL COMMENT '操作员名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品信息表';

-- --------------------------------------------------------

--
-- Table structure for table `t_product_filter`
--

DROP TABLE IF EXISTS `t_product_filter`;
CREATE TABLE IF NOT EXISTS `t_product_filter` (
  `id` int(10) unsigned NOT NULL COMMENT '过滤器ID',
  `filter_name` varchar(64) DEFAULT NULL COMMENT '过滤器名称',
  `filter_option` varchar(255) DEFAULT NULL COMMENT '过滤器选值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `operator_id` int(10) unsigned DEFAULT NULL COMMENT '操作员ID',
  `operator_name` varchar(64) DEFAULT NULL COMMENT '操作员名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品过滤器表';

-- --------------------------------------------------------

--
-- Table structure for table `t_product_require`
--

DROP TABLE IF EXISTS `t_product_require`;
CREATE TABLE IF NOT EXISTS `t_product_require` (
  `id` int(10) unsigned NOT NULL COMMENT '序列号',
  `product_id` int(10) unsigned DEFAULT NULL COMMENT '产品ID',
  `product_name` varchar(128) DEFAULT NULL COMMENT '产品名称',
  `require_id` int(10) unsigned DEFAULT NULL COMMENT '订单要求ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品下单要求表';

-- --------------------------------------------------------

--
-- Table structure for table `t_product_type`
--

DROP TABLE IF EXISTS `t_product_type`;
CREATE TABLE IF NOT EXISTS `t_product_type` (
  `id` int(10) unsigned NOT NULL COMMENT '分类ID',
  `name` varchar(64) DEFAULT NULL COMMENT '分类名称',
  `parent_id` int(10) unsigned DEFAULT NULL COMMENT '父分类ID',
  `parent_name` varchar(64) DEFAULT NULL COMMENT '父分类名称',
  `class_id` int(10) unsigned DEFAULT NULL COMMENT '分类标准ID',
  `class_name` varchar(64) DEFAULT NULL COMMENT '分类标准名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `operator_id` int(10) unsigned DEFAULT NULL COMMENT '操作员ID',
  `operator_name` varchar(64) DEFAULT NULL COMMENT '操作员名称',
  `note` varchar(255) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品分类表';

-- --------------------------------------------------------

--
-- Table structure for table `t_require`
--

DROP TABLE IF EXISTS `t_require`;
CREATE TABLE IF NOT EXISTS `t_require` (
  `id` int(10) unsigned NOT NULL COMMENT '要求ID',
  `field_name` varchar(64) DEFAULT NULL COMMENT '要求信息字段名称',
  `field_value` varchar(255) DEFAULT NULL COMMENT '要求信息字段值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='要求信息表';

-- --------------------------------------------------------

--
-- Table structure for table `t_review`
--

DROP TABLE IF EXISTS `t_review`;
CREATE TABLE IF NOT EXISTS `t_review` (
  `id` int(10) unsigned NOT NULL COMMENT '评论ID',
  `object_id` int(11) DEFAULT NULL COMMENT '评论对象ID',
  `object_name` varchar(64) DEFAULT NULL COMMENT '评论对象名称',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `review_content` text COMMENT '评论内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `parent_id` int(10) unsigned DEFAULT NULL COMMENT '父ID',
  `parent_name` varchar(64) DEFAULT NULL COMMENT '父名称',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态：0-正常；1-违规；',
  `operator_id` int(10) unsigned DEFAULT NULL COMMENT '操作员ID',
  `operator_name` varchar(64) DEFAULT NULL COMMENT '操作员名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论记录表';

-- --------------------------------------------------------

--
-- Table structure for table `t_search`
--

DROP TABLE IF EXISTS `t_search`;
CREATE TABLE IF NOT EXISTS `t_search` (
  `id` int(10) unsigned NOT NULL COMMENT '搜索词ID',
  `keyword` varchar(255) DEFAULT NULL COMMENT '关键词',
  `count` int(10) unsigned DEFAULT NULL COMMENT '搜索次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='搜索关键词表';

-- --------------------------------------------------------

--
-- Table structure for table `t_settings`
--

DROP TABLE IF EXISTS `t_settings`;
CREATE TABLE IF NOT EXISTS `t_settings` (
  `id` int(10) unsigned NOT NULL COMMENT 'ID',
  `name` varchar(128) DEFAULT NULL COMMENT '设置项名称',
  `value` varchar(255) DEFAULT NULL COMMENT '设置项值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `operator_id` int(10) unsigned DEFAULT NULL COMMENT '操作员ID',
  `operator_name` varchar(64) DEFAULT NULL COMMENT '操作员名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设置信息表';

-- --------------------------------------------------------

--
-- Table structure for table `t_sliders`
--

DROP TABLE IF EXISTS `t_sliders`;
CREATE TABLE IF NOT EXISTS `t_sliders` (
  `id` int(10) unsigned NOT NULL COMMENT 'ID',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `position` int(11) DEFAULT NULL COMMENT '位置：',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  `pic1` varchar(255) DEFAULT NULL COMMENT '图片1',
  `pic2` varchar(255) DEFAULT NULL COMMENT '图片2',
  `pic3` varchar(255) DEFAULT NULL COMMENT '图片3',
  `pic4` varchar(255) DEFAULT NULL COMMENT '图片4',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='轮播图配置表';

-- --------------------------------------------------------

--
-- Table structure for table `t_sponsor`
--

DROP TABLE IF EXISTS `t_sponsor`;
CREATE TABLE IF NOT EXISTS `t_sponsor` (
  `id` int(10) unsigned NOT NULL COMMENT '主办方ID',
  `name` varchar(64) DEFAULT NULL COMMENT '主办方名称',
  `company_name` varchar(64) DEFAULT NULL COMMENT '公司名称',
  `company_address` varchar(128) DEFAULT NULL COMMENT '公司地址',
  `company_phone` varchar(11) DEFAULT NULL COMMENT '公司电话',
  `company_pic` varchar(64) DEFAULT NULL COMMENT '公司负责人',
  `rating` int(11) DEFAULT NULL COMMENT '评级',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `verify_status` tinyint(4) DEFAULT NULL COMMENT '审核状态：0-未审核；1-已审核；2-审核失败',
  `verify_time` datetime DEFAULT NULL COMMENT '审核时间',
  `operator_id` int(10) unsigned DEFAULT NULL COMMENT '操作员ID',
  `operator_time` datetime DEFAULT NULL COMMENT '操作员名称',
  `short_intro` varchar(128) DEFAULT NULL COMMENT '简要信息',
  `long_intro` text COMMENT '详细信息',
  `is_recommend` tinyint(4) DEFAULT NULL COMMENT '是否推荐：0-不推荐；1-推荐',
  `title` varchar(64) DEFAULT NULL COMMENT '显示标题'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主办方信息表';

-- --------------------------------------------------------

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE IF NOT EXISTS `t_user` (
  `id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '用户昵称',
  `true_name` varchar(64) DEFAULT NULL COMMENT '真实姓名',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮箱',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_login` tinyint(4) DEFAULT NULL COMMENT '是否登录',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `wx_openid` varchar(64) DEFAULT NULL COMMENT '微信OPENID',
  `wx_name` varchar(64) DEFAULT NULL COMMENT '微信名称',
  `wx_avatar` varchar(255) DEFAULT NULL COMMENT '微信头像',
  `wx_nick_name` varchar(64) DEFAULT NULL COMMENT '微信昵称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基础表';

-- --------------------------------------------------------

--
-- Table structure for table `t_user_product`
--

DROP TABLE IF EXISTS `t_user_product`;
CREATE TABLE IF NOT EXISTS `t_user_product` (
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `product_id` int(10) unsigned DEFAULT NULL COMMENT '产品ID',
  `product_name` varchar(128) DEFAULT NULL COMMENT '产品名称',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态：0-参与过的；1-已报名的；',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `id` int(10) unsigned NOT NULL COMMENT '序号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户产品表';

-- --------------------------------------------------------

--
-- Table structure for table `t_user_share`
--

DROP TABLE IF EXISTS `t_user_share`;
CREATE TABLE IF NOT EXISTS `t_user_share` (
  `id` int(10) unsigned NOT NULL COMMENT '序号',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `share_content` text COMMENT '分享内容',
  `share_way` tinyint(4) DEFAULT NULL COMMENT '分享方式',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户分享记录表';

-- --------------------------------------------------------

--
-- Table structure for table `t_user_sponsor`
--

DROP TABLE IF EXISTS `t_user_sponsor`;
CREATE TABLE IF NOT EXISTS `t_user_sponsor` (
  `id` int(10) unsigned NOT NULL COMMENT '序号',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `user_name` varchar(64) NOT NULL COMMENT '用户名称',
  `sponsor_id` int(10) unsigned NOT NULL COMMENT '主办方ID',
  `sponsor_name` varchar(128) NOT NULL COMMENT '主办方名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户关注主办方表';

--
-- Indexes for dumped tables
--

--
-- Indexes for table `t_city`
--
ALTER TABLE `t_city`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_feedback`
--
ALTER TABLE `t_feedback`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_order`
--
ALTER TABLE `t_order`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `order_id` (`order_id`);

--
-- Indexes for table `t_product`
--
ALTER TABLE `t_product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_product_filter`
--
ALTER TABLE `t_product_filter`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_product_require`
--
ALTER TABLE `t_product_require`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_product_type`
--
ALTER TABLE `t_product_type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `t_review`
--
ALTER TABLE `t_review`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_search`
--
ALTER TABLE `t_search`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `keyword` (`keyword`);

--
-- Indexes for table `t_settings`
--
ALTER TABLE `t_settings`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `t_sliders`
--
ALTER TABLE `t_sliders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_sponsor`
--
ALTER TABLE `t_sponsor`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_user`
--
ALTER TABLE `t_user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_user_product`
--
ALTER TABLE `t_user_product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_user_share`
--
ALTER TABLE `t_user_share`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_user_sponsor`
--
ALTER TABLE `t_user_sponsor`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `t_city`
--
ALTER TABLE `t_city`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '城市ID';
--
-- AUTO_INCREMENT for table `t_feedback`
--
ALTER TABLE `t_feedback`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '反馈ID';
--
-- AUTO_INCREMENT for table `t_order`
--
ALTER TABLE `t_order`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内部id';
--
-- AUTO_INCREMENT for table `t_product_filter`
--
ALTER TABLE `t_product_filter`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '过滤器ID';
--
-- AUTO_INCREMENT for table `t_product_require`
--
ALTER TABLE `t_product_require`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '序列号';
--
-- AUTO_INCREMENT for table `t_product_type`
--
ALTER TABLE `t_product_type`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '分类ID';
--
-- AUTO_INCREMENT for table `t_search`
--
ALTER TABLE `t_search`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '搜索词ID';
--
-- AUTO_INCREMENT for table `t_settings`
--
ALTER TABLE `t_settings`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID';
--
-- AUTO_INCREMENT for table `t_sliders`
--
ALTER TABLE `t_sliders`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID';
--
-- AUTO_INCREMENT for table `t_user_product`
--
ALTER TABLE `t_user_product`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '序号';
--
-- AUTO_INCREMENT for table `t_user_share`
--
ALTER TABLE `t_user_share`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '序号';
--
-- AUTO_INCREMENT for table `t_user_sponsor`
--
ALTER TABLE `t_user_sponsor`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '序号';SET FOREIGN_KEY_CHECKS=1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
