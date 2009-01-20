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

  def set_tile(n, value)
    row = row_index(n)
    col = col_index(n)
    @matrix[row][col] = value
  end

  def get_tile(n)
    row = row_index(n)
    col = col_index(n)
    @matrix[row][col]
  end

  def row_index(n)
    n / 9
  end

  def col_index(n)
    n % 9
  end

  def to_s
    @matrix.map {|r| r.inspect + "\n" }.to_s
  end
    
end

class SudokuGame 
  attr_accessor :sudoku, :visible_sudoku
  def initialize 
    @sudoku = generate_sudoku
    @visible_sudoku = overlay_facade(@sudoku)

    @glade = GladeXML.new("test.xml") {|handler| method(handler)}
    @appwindow = @glade.get_widget("appwindow")
    fill_entries
    @appwindow.show_all
  end

  def fill_entries
    for row in 1..9
      for col in 1..9
        entry = @glade.get_widget("entry#{row}#{col}")
        entry.text = eval("#{row}#{col}")
      end
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
      n = rand(81)
      value = sudoku.get_tile(n)
      facade.set_tile(n, value)
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

