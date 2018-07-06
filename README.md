# data-fetcher

A Clojure library designed to ... well, that part is up to you.

## Usage

FIXME

## TODO

- Replace this with the method from `(spit "output.txt" (slurp ...))`

- https://clojuredocs.org/clojure.core/slurp

```sh
set -evx

mkdir -p data/mr-data
cd data/mr-data
wget https://raw.githubusercontent.com/yoonkim/CNN_sentence/master/rt-polarity.neg
wget https://raw.githubusercontent.com/yoonkim/CNN_sentence/master/rt-polarity.pos
cd ../..
mkdir -p data/glove
cd data/glove
wget http://nlp.stanford.edu/data/glove.6B.zip
unzip *.zip
cd ../..
```
## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
