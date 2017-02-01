GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [dbo].[sing_payment](
	[sing_payment_id] [bigint] IDENTITY(1,1) NOT NULL,
	[customer_name] [varchar](100) NULL,
	[customer_phone] [varchar](50) NULL,
	[currency] [varchar](10) NULL,
	[price] [varchar](10) NULL,
	[credit_card_holder_name] [varchar](100) NULL,
	[credit_card_exp_mon] [varchar](2) NULL,
	[credit_card_number] [varchar](50) NULL,
	[credit_card_exp_year] [varchar](4) NULL,
	[credit_card_ccv] [varchar](4) NULL,
	[payment_method] [varchar](100) NULL,
	[remark] [varchar](max) NULL,
	[process_status] [varchar](50) NULL,
	[refer_code] [varchar](500) NULL,
	[card_type] [varchar](50) NULL,
	[create_date] [datetime] NOT NULL,
	[last_modified_date] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [dbo].[sing_payment_AUD](
	[REV] [int] NOT NULL,
	[sing_payment_id] [bigint] NULL,
	[customer_name] [varchar](100) NULL,
	[customer_phone] [varchar](50) NULL,
	[currency] [varchar](10) NULL,
	[price] [varchar](10) NULL,
	[credit_card_holder_name] [varchar](100) NULL,
	[credit_card_exp_mon] [varchar](2) NULL,
	[credit_card_number] [varchar](50) NULL,
	[credit_card_exp_year] [varchar](4) NULL,
	[credit_card_ccv] [varchar](4) NULL,
	[payment_method] [varchar](100) NULL,
	[remark] [varchar](max) NULL,
	[process_status] [varchar](50) NULL,
	[refer_code] [varchar](500) NULL,
	[card_type] [varchar](50) NULL,
	[create_date] [datetime] NOT NULL,
	[last_modified_date] [datetime] NULL,
	[REVTYPE] [smallint] NULL
)


GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[revinfo](
	[REV] [int] IDENTITY(1,1) NOT NULL,
	[REVTSTMP] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[REV] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


