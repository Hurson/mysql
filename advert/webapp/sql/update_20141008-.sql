-- 单向实时广告，原来不区分地市，现在把ad_playlist_gis表一条数据分成18条,现网热点推荐的选择是全省，也要拆分分18条
CREATE TABLE ad_playlist_gis_bak917 AS SELECT
	*
FROM
	ad_playlist_gis;

CREATE TABLE poly_tmp1 AS SELECT DISTINCT
	PLOY_ID
FROM
	t_ploy t
WHERE
	t.POSITION_ID IN (
		5,
		6,
		7,
		8,
		9,
		10,
		41,
		42,
		11,
		12,
		3,
		4,
		21,
		22
	);

INSERT INTO ad_playlist_gis (
	PLOY_ID,
	START_TIME,
	END_TIME,
	CONTENT_PATH,
	CONTENT_TYPE,
	AD_SITE_CODE,
	CHARACTERISTIC_IDENTIFICATION,
	SERVICE_ID,
	AREAS,
	USERINDUSTRYS,
	USERLEVELS,
	TVN,
	STATE,
	CONTRACT_ID,
	ORDER_ID,
	CONTENT_ID,
	CATEGORY_ID,
	ASSET_ID
) SELECT
	a.PLOY_ID PLOY_ID,
	a.START_TIME START_TIME,
	a.END_TIME END_TIME,
	a.CONTENT_PATH CONTENT_PATH,
	a.CONTENT_TYPE CONTENT_TYPE,
	a.AD_SITE_CODE AD_SITE_CODE,
	a.CHARACTERISTIC_IDENTIFICATION CHARACTERISTIC_IDENTIFICATION,
	a.SERVICE_ID SERVICE_ID,
	tr.AREA_CODE AREAS,
	a.USERINDUSTRYS USERINDUSTRYS,
	a.USERLEVELS USERLEVELS,
	a.TVN TVN,
	a.STATE STATE,
	a.CONTRACT_ID CONTRACT_ID,
	a.ORDER_ID ORDER_ID,
	a.CONTENT_ID CONTENT_ID,
	a.CATEGORY_ID CATEGORY_ID,
	a.ASSET_ID ASSET_ID
FROM
	ad_playlist_gis a,
	t_release_area tr
WHERE
	(a.STATE = 0 OR a.STATE = 1)
AND (
	(
		a.PLOY_ID IN (
			SELECT DISTINCT
				PLOY_ID
			FROM
				poly_tmp1 t
		)
	)
	OR a.AREAS = '152000000000'
)
AND tr.AREA_CODE != '152000000000';

CREATE TABLE ad_playlist_gis_tmp AS SELECT
	ID
FROM
	ad_playlist_gis_bak917 a
WHERE
	(a.STATE = 0 OR a.STATE = 1)
AND (
	(
		a.PLOY_ID IN (
			SELECT DISTINCT
				PLOY_ID
			FROM
				poly_tmp1 t
		)
	)
	OR a.AREAS = '152000000000'
);

DELETE
FROM
	ad_playlist_gis
WHERE
	id IN (
		SELECT
			ID
		FROM
			ad_playlist_gis_tmp
	);

DROP TABLE ad_playlist_gis_tmp;

DROP TABLE poly_tmp1;

-- t_order_mate_rel表增加字段后，把t_ploy表相应属性迁移过来
CREATE TABLE poly_tmp AS SELECT
	id,

IF (
	START_TIME = '0',
	'00:00:00',
	START_TIME
) AS START_TIME,

IF (
	END_TIME = '0',
	'23:59:59',
	END_TIME
) AS END_TIME,
 AREA_ID,
 CHANNEL_GROUP_ID
FROM
	t_ploy t;

CREATE TABLE t_order_mate_rel_bak AS SELECT
	*
FROM
	t_order_mate_rel;

UPDATE t_order_mate_rel t
SET START_TIME = '00:00:00',
 END_TIME = '23:59:59';

UPDATE t_order_mate_rel t
SET START_TIME = (
	SELECT
		START_TIME
	FROM
		poly_tmp p
	WHERE
		t.PRECISE_ID = p.id
),
 END_TIME = (
	SELECT
		END_TIME
	FROM
		poly_tmp p
	WHERE
		t.PRECISE_ID = p.id
),
 area_code = (
	SELECT
		AREA_ID
	FROM
		poly_tmp p
	WHERE
		t.PRECISE_ID = p.id
),
 channel_group_id = (
	SELECT
		CHANNEL_GROUP_ID
	FROM
		poly_tmp p
	WHERE
		t.PRECISE_ID = p.id
)
WHERE
	type = 1;

DROP TABLE poly_tmp;

-- 单向实时广告，原来不区分地市，现在把t_order_mate_rel表一条数据分成18条,现网热点推荐的选择是全省，也要拆分分18条
INSERT INTO t_order_mate_rel (
	ORDER_ID,
	MATE_ID,
	PLAY_LOCATION,
	IS_HD,
	POLL_INDEX,
	PRECISE_ID,
	type,
	start_time,
	end_time,
	area_code,
	channel_group_id,
	SERVICE_ID
) SELECT
	tomr.ORDER_ID ORDER_ID,
	tomr.MATE_ID MATE_ID,
	tomr.PLAY_LOCATION PLAY_LOCATION,
	tomr.IS_HD IS_HD,
	tomr.POLL_INDEX POLL_INDEX,
	tomr.PRECISE_ID PRECISE_ID,
	tomr.type type,
	tomr.start_time start_time,
	tomr.end_time end_time,
	tr.AREA_CODE area_code,
	tomr.channel_group_id channel_group_id,
	tomr.SERVICE_ID SERVICE_ID
FROM
	t_order_mate_rel tomr,
	t_release_area tr
WHERE
	(
		(
			tomr.ORDER_ID IN (
				SELECT
					tomr.ID
				FROM
					t_order tomr
				WHERE
					tomr.PLOY_ID IN (
						SELECT DISTINCT
							PLOY_ID
						FROM
							t_ploy tp
						WHERE
							tp.POSITION_ID IN (
								5,
								6,
								7,
								8,
								9,
								10,
								41,
								42,
								11,
								12,
								3,
								4,
								21,
								22
							)
					)
			)
		)
		OR tomr.area_code = '152000000000'
	)
AND tr.AREA_CODE != '152000000000';

CREATE TABLE t_order_mate_rel_tmptmp AS SELECT
	ID
FROM
	t_order_mate_rel_bak tomr
WHERE
	(
		tomr.ORDER_ID IN (
			SELECT
				tomr.ID
			FROM
				t_order tomr
			WHERE
				tomr.PLOY_ID IN (
					SELECT DISTINCT
						PLOY_ID
					FROM
						t_ploy tp
					WHERE
						tp.POSITION_ID IN (
							5,
							6,
							7,
							8,
							9,
							10,
							41,
							42,
							11,
							12,
							3,
							4,
							21,
							22
						)
				)
		)
	)
OR tomr.area_code = '152000000000';

DELETE
FROM
	t_order_mate_rel
WHERE
	ID IN (
		SELECT
			ID
		FROM
			t_order_mate_rel_tmptmp
	);

DROP TABLE t_order_mate_rel_tmptmp;

