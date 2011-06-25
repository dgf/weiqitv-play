SELECT
        g.onlineId
        ,wp.name AS 'white'
        ,wr.nr
        ,wr. TYPE
        ,bp.name AS 'black'
        ,br.nr
        ,br. TYPE
    FROM
        Game g
            JOIN GameServer s
                ON s.id = s.server_id
            JOIN GamePlayer w
                ON w.id = g.white_id
            JOIN Player wp
                ON wp.id = w.player_id
            JOIN Rank wr
                ON wr.id = w.rank_id
            JOIN GamePlayer b
                ON b.id = g.black_id
            JOIN Player bp
                ON bp.id = b.player_id
            JOIN Rank br
                ON br.id = b.rank_id;
