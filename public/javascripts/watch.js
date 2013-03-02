/*global jQuery, CHANNEL*/
(function ($, socket, listMoves) {

  var player = 'black'
  var other = 'white'

  function viewPrisoner(tPlayer, coordinate) {

    // remove stone
    $('#' + coordinate).removeClass('white black').addClass('empty').empty()

    // update prisoner list of player
    var dl = $('#' + tPlayer + '_prisoners')
    dl.append('<dd></dd>')

    // update prisoner count
    var dt = $('#' + tPlayer + '_prisoner_count')
    var count = parseInt(dt.text())
    dt.text(count + 1)
  }

  function updateTime() {
    var time = $('#' + other + '_time').text()
    $('#' + other + '_time').empty().text(parseInt(time) - 1)
  }

  $(document).ready(function () {

    // event socket callback
    var display = function (event) {
      $('#commentList').prepend('<li class="igs">' + event + '</li>')
      eval('e = ' + event)
      switch (e.type) {

        case 'message':
          // show broadcast or result
          $('#result').empty().text(e.message).css('visibility', 'visible')
          break

        case 'move':
          player = e.player.toLowerCase()
          other = player == 'black' ? 'white' : 'black'

          // replace last move
          $('.' + other + 'last').removeClass(other + 'last').addClass(other)

          // show stone and number
          $('#' + e.coordinate).removeClass('empty black white ko')
            .addClass(player + 'last').empty()

          // remove prisoners
          $.each(e.prisoners, function (p, prisoner) {
            viewPrisoner(e.player, prisoner)
          })

          // set player infos
          $('#' + player + '_time').empty().text(e.time)
          $('#' + player + '_byo').empty().text(e.byo)

          // set game info
          $('#turn').empty().text(e.coordinate)
          break

        case 'next':
          // blank the whole view for next game
          $('table tr td div').removeClass('empty black white ko').addClass('empty')
          $('#result').empty().css('visibility', 'hidden')

          $('#game').empty().text(e.game)
          $('#turn').empty().text(e.turn)
          $('#white_name').empty().text(e.white)
          $('#white_time').empty().text(e.wTime)
          $('#white_prisoner_count').empty().text(0)
          $('#white_prisoners dd').remove()
          $('#black_name').empty().text(e.black)
          $('#black_time').empty().text(e.bTime)
          $('#black_prisoner_count').empty().text(0)
          $('#black_prisoners dd').remove()

          $('#handicap').empty().text(e.handicap)
          $('#byo').empty().text(e.byo)
          $('#komi').empty().text(e.komi)
          break
        default:
          console.log('unknown socket event ' + event)
          break
      }
    }

    // bind socket event callback
    socket.onmessage = function (event) {
      display(event.data)
    }

    // initialize board
    $.getJSON(listMoves(), function (data) {
      $.each(data, function (key, turn) {
        if (turn.number == 0) {
          console.log(data)
        }

        // set actual move
        $('#' + turn.coordinate).removeClass('empty').addClass(turn.player)

        // blank prisoners
        if (turn.prisoners && turn.prisoners.length) {
          $.each(turn.prisoners, function (p, prisoner) {
            viewPrisoner(turn.player, prisoner)
          })
        }
      })
      // start timer update
      setInterval(updateTime, 1000)
    })
  })
}(jQuery, CHANNEL.socket, CHANNEL.moves))
