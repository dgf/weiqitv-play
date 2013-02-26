SELECT
        s.host
        ,g.created
        ,g.updated
        ,g.onlineId
        ,wp.name AS 'white'
        ,wr.nr
        ,wr.type
        ,bp.name AS 'black'
        ,br.nr
        ,br.type
        ,h.stones
        ,g.byo
        ,g.komi

FROM
        Game g
            JOIN GameServer s
                ON s.id = g.server_id
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
                ON br.id = b.rank_id
            JOIN Handicap h
                ON h.id = g.handicap_id;
