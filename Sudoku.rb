require 'gtk2'
require 'libglade2'

SUDOKU_JAR = "target/sudoku-1.0-SNAPSHOT-jar-with-dependencies.jar"

class SudokuMatrix 
  attr_accessor :matrix
  def initialize(_matrix = [])
    @matrix = _matrix
    if @matrix == []
      9.times do 
        @matrix << [0] * 9
      end
    end
  end

  def set_tile(row, col, value)
    @matrix[dec(row)][dec(col)] = value
  end

  def get_tile(row,col)
    @matrix[dec(row)][dec(col)]
  end

  def to_s
    @matrix.map {|r| r.inspect + "\n" }.to_s
  end

  def dec(n)
    if n == 0
      0
    else 
      n - 1
    end
  end
    
end

def show_msg(window, msg)
  msg = Gtk::MessageDialog.new(window,
                               Gtk::Dialog::DESTROY_WITH_PARENT,
                               Gtk::MessageDialog::QUESTION,
                               Gtk::MessageDialog::BUTTONS_CLOSE,
                               msg)
  msg.run
  msg.destroy
end

class SudokuGame 
  attr_accessor :sudoku, :visible_sudoku
  def initialize 
    @sudoku = generate_sudoku
    @visible_sudoku = overlay_facade(@sudoku)
    puts @sudoku.to_s
    puts "=========="
    puts @visible_sudoku.to_s

    @glade = GladeXML.new("test2.xml") {|handler| method(handler)}
    @appwindow = @glade.get_widget("appwindow")
    fill_entries
    @appwindow.signal_connect("delete_event") { false }
    @appwindow.signal_connect("destroy") { Gtk.main_quit }

    @correct_button = @glade.get_widget("correct?")
    @solve_button = @glade.get_widget("solve")

    @correct_button.signal_connect("clicked") { correct? }

    @appwindow.show_all
  end

  def fill_entries
    for row in 1..9
      for col in 1..9
        entry = @glade.get_widget("entry#{row}#{col}")
        value = @visible_sudoku.get_tile(row, col)
        if value == 0
          entry.text = ""
        else
          entry.text = value.to_s
        end
      end
    end
  end

  def correct?
    correct = true
    for row in 1..9
      for col in 1..9
        entry = @glade.get_widget("entry#{row}#{col}")
        answer_value = @sudoku.get_tile(row,col)
        if entry.text.to_i != answer_value 
          correct = false
        end
      end
    end

    if correct
      show_msg(@appwindow, "Correct!!")
    else
      show_msg(@appwindow, "Incorrect!!")
    end
  end
          

  def generate_sudoku
    raw_sudoku = `java -cp #{SUDOKU_JAR} org.nrh.sudoku.Main 1`
    raw_sudoku.strip!
    raw_sudoku.gsub!(/(\d) \n/, "\\1],\n")    
    raw_sudoku.gsub!(/(\d) /,'\1,')
    raw_sudoku.gsub!(/^(\d)/,'[\1')
    raw_sudoku = "[" + raw_sudoku + "]]"
    return SudokuMatrix.new(eval(raw_sudoku))
  end

  def overlay_facade(sudoku)
    facade = SudokuMatrix.new
    for x in 1..30
      row = rand(9) + 1
      col = rand(9) + 1
      value = sudoku.get_tile(row, col)
      facade.set_tile(row, col, value)
    end
    return facade
  end

  def to_s 
    return @sudoku.to_s
  end
end

sg = SudokuGame.new
#puts sg.visible_sudoku.to_s
Gtk.main

