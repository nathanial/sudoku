require 'gtk2'
require 'libglade2'

class SudokuGame 
  def initialize 
    @glade = GladeXML.new("Sudoku.glade") {|handler| method(handler)}
    @appwindow = @glade.get_widget("appwindow")
    @appwindow.show_all
  end
end

SudokuGame.new
Gtk.main
