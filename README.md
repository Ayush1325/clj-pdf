# A Library for easily generating PDFs from Clojure 

## Usage

write-doc will produce a PDF given a vector which defines the document and write it to out which can be either a string, in which case it's treated as a file name, or an output stream. The document format is as follows:

### Metadata

All fields in the metadata section are optional:

    {:title  "Test doc"
     :left-margin   10
     :right-margin  10
     :top-margin    20
     :bottom-margin 25
     :subject "Some subject"
     :author "John Doe"
     :creator "Jane Doe"
     :header ["inspired by" "William Shakespeare"]}

### Document sections

Each document section is represented by a vector starting with a keyword identifying the section followed by the contents of the section.

#### Font

A font is defined by a map consisting of the following parameters, all parameters are optional

* :family has following options: :courier, :helvetica, :times-roman, :symbol, :zapfdingbats defaults to :helvetica
* :size is a number default is 11
* :style has following options: :bold, :italic, :bold-italic, :normal, :strikethru, :underline defaults to :normal
* :color is a vector of [r g b] defaults to black

example font:

    {:style :bold
     :size 18
     :family :helvetica
     :color [0 234 123]}


#### Chunk 

Chunk is the smallest component, it consists of the keyword :chunk a font and the content string 

   [:chunk {:style :bold} "small chunk of text"]

#### Phrase

A phrase consists of the keyword :phrase followed by the contents, the first item of contents is the font metadata (can be empty), followed by the rest of the contents, which can conist of chunks or strings:

    [:phrase {} "some text here"]

    [:phrase {:style :bold :size 18 :family :halvetica :color [0 255 221]} "Hello Clojure!"]
  
    [:phrase [:chunk {:style :italic} "chunk one"] [:chunk {:size 20} "Big text"] "some other text"]

#### Paragraph

A paragraph consists of the keyword :paragraph followed by the content which can consists of either a string or a phrase

    [:paragraph "a fine paragraph"]

    [:paragraph [:phrase {:style :bold :size 18 :family :halvetica :color [0 255 221]} "Hello Clojure!"]]

#### Chapter

A chapter has the following form, it starts with the keyword :chapter followed by the number of the chapter, and optionally by a title. The title can be either a string or a paragraph:


    [:chapter 1]

    [:chapter 2 "Second Chapter"]

    [:chapter 3 [:paragraph "Third Chapter]]

#### List

A list consists of the keyword :list followed by the metadata and the contents, the metadata can consist of the following, all omitable:

* :numbered boolean
* :lettered boolean
* :roman    boolean

the rest of the contents can be either strings, phrases, or chunks

   [:list {:roman true} [:chunk {:style :bold} "a bold item"] "another item" "yet another item"]


# TODO:

* support for images
* page sizes
* suggestions welcome :)







