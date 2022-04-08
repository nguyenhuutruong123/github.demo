/****** Object:  Table [dbo].[THONGBAO]    Script Date: 02/13/2014 11:07:04 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[THONGBAO](
	[pk_seq] [numeric](18, 0) IDENTITY(100000,1) NOT FOR REPLICATION NOT NULL,
	[tieude] [nvarchar](200) NOT NULL,
	[noidung] [nvarchar](max) NOT NULL,
	[nguoitao] [numeric](18, 0) NULL,
	[ngaytao] [varchar](10) NULL,
	[nguoisua] [numeric](18, 0) NULL,
	[ngaysua] [varchar](10) NULL,
	[ngaybatdau] [varchar](10) NULL,
	[ngayketthuc] [varchar](10) NULL,
	[trangthai] [varchar](1) NULL,
	[filename] [nvarchar](200) NULL,
	[loaithongbao] [tinyint] NULL,
	[hethieuluc] [tinyint] NULL,
	[tinhtrang] [tinyint] NULL,
 CONSTRAINT [PK_THONGBAO] PRIMARY KEY CLUSTERED 
(
	[pk_seq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[THONGBAO] ADD  DEFAULT ((0)) FOR [loaithongbao]
GO

ALTER TABLE [dbo].[THONGBAO] ADD  DEFAULT ((0)) FOR [hethieuluc]
GO

ALTER TABLE [dbo].[THONGBAO] ADD  DEFAULT ((0)) FOR [tinhtrang]
GO

/****** Object:  Table [dbo].[THONGBAO_DDKD]    Script Date: 02/13/2014 11:07:19 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[THONGBAO_DDKD](
	[thongbao_fk] [numeric](18, 0) NOT NULL,
	[ddkd_fk] [numeric](18, 0) NOT NULL,
	[trangthai] [tinyint] NULL,
 CONSTRAINT [PK_THONGBAO_DDKD] PRIMARY KEY CLUSTERED 
(
	[thongbao_fk] ASC,
	[ddkd_fk] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[THONGBAO_DDKD_TRALOI]    Script Date: 02/13/2014 11:07:34 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[THONGBAO_DDKD_TRALOI](
	[pk_seq] [numeric](18, 0) IDENTITY(100000,1) NOT NULL,
	[thongbao_fk] [numeric](18, 0) NOT NULL,
	[ddkd_fk] [numeric](18, 0) NOT NULL,
	[traloi] [text] NOT NULL,
	[thoidiem] [datetime] NOT NULL,
	[nguoithuchien] [numeric](18, 0) NULL,
 CONSTRAINT [PK_THONGBAO_DDKD_TRALOI] PRIMARY KEY CLUSTERED 
(
	[pk_seq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

ALTER TABLE [dbo].[THONGBAO_DDKD_TRALOI]  WITH NOCHECK ADD  CONSTRAINT [ddkd_fk3] FOREIGN KEY([ddkd_fk])
REFERENCES [dbo].[DAIDIENKINHDOANH] ([PK_SEQ])
GO

ALTER TABLE [dbo].[THONGBAO_DDKD_TRALOI] CHECK CONSTRAINT [ddkd_fk3]
GO

ALTER TABLE [dbo].[THONGBAO_DDKD_TRALOI]  WITH NOCHECK ADD  CONSTRAINT [thongbao_fk1] FOREIGN KEY([thongbao_fk])
REFERENCES [dbo].[THONGBAO] ([pk_seq])
GO

ALTER TABLE [dbo].[THONGBAO_DDKD_TRALOI] CHECK CONSTRAINT [thongbao_fk1]
GO

ALTER TABLE [dbo].[THONGBAO_DDKD_TRALOI] ADD  CONSTRAINT [DF_thongbao_ddkd_traloi_thoidiem]  DEFAULT (getdate()) FOR [thoidiem]
GO



/****** Object:  Table [dbo].[THONGBAO_NHANVIEN]    Script Date: 02/13/2014 11:07:48 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[THONGBAO_NHANVIEN](
	[thongbao_fk] [numeric](18, 0) NOT NULL,
	[nhanvien_fk] [numeric](18, 0) NOT NULL,
	[trangthai] [tinyint] NULL,
 CONSTRAINT [PK_THONGBAO_NHANVIEN] PRIMARY KEY CLUSTERED 
(
	[thongbao_fk] ASC,
	[nhanvien_fk] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

/****** Object:  Table [dbo].[THONGBAO_NHANVIEN_TRALOI]    Script Date: 02/13/2014 11:07:59 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[THONGBAO_NHANVIEN_TRALOI](
	[thongbao_fk] [numeric](18, 0) NULL,
	[nhanvien_fk] [numeric](18, 0) NULL,
	[nguoithuchien] [numeric](18, 0) NULL,
	[phanloai] [tinyint] NULL,
	[thoidiem] [datetime] NULL,
	[noidung] [nvarchar](500) NULL
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[THONGBAO_NHANVIEN_TRALOI] ADD  DEFAULT ((0)) FOR [phanloai]
GO

ALTER TABLE [dbo].[THONGBAO_NHANVIEN_TRALOI] ADD  DEFAULT (getdate()) FOR [thoidiem]
GO



/****** Object:  Table [dbo].[THONGBAO_VBCC]    Script Date: 02/13/2014 11:08:09 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[THONGBAO_VBCC](
	[thongbao_fk] [numeric](18, 0) NOT NULL,
	[vbcc_fk] [numeric](18, 0) NOT NULL,
	[trangthai] [tinyint] NULL
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[THONGBAO_VBCC] ADD  DEFAULT ((0)) FOR [trangthai]
GO


/****** Object:  Table [dbo].[THONGBAO_VBHD]    Script Date: 02/13/2014 11:08:18 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[THONGBAO_VBHD](
	[thongbao_fk] [numeric](18, 0) NOT NULL,
	[vbhd_fk] [numeric](18, 0) NOT NULL,
	[trangthai] [tinyint] NULL
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[THONGBAO_VBHD] ADD  DEFAULT ((0)) FOR [trangthai]
GO


/****** Object:  Table [dbo].[THONGBAO_VBSDBS]    Script Date: 02/13/2014 11:08:27 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[THONGBAO_VBSDBS](
	[thongbao_fk] [numeric](18, 0) NOT NULL,
	[vbsdbs_fk] [numeric](18, 0) NOT NULL,
	[trangthai] [tinyint] NULL
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[THONGBAO_VBSDBS] ADD  DEFAULT ((0)) FOR [trangthai]
GO



/****** Object:  Table [dbo].[THONGBAO_VBTT]    Script Date: 02/13/2014 11:08:38 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[THONGBAO_VBTT](
	[thongbao_fk] [numeric](18, 0) NOT NULL,
	[vbtt_fk] [numeric](18, 0) NOT NULL,
	[trangthai] [tinyint] NULL
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[THONGBAO_VBTT] ADD  DEFAULT ((0)) FOR [trangthai]
GO


