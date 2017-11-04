# Gator

[![Travis](https://travis-ci.org/Virtlink/gator.svg?branch=master)][1]
[![GitHub release](https://img.shields.io/github/release/virtlink/gator.svg)][2]
[![Docs](https://img.shields.io/badge/docs-latest-brightgreen.svg)][3]
[![Maintenance](https://img.shields.io/maintenance/yes/2017.svg)][4]
[![License](https://img.shields.io/github/license/Virtlink/gator.svg)][5]

Generates source files based on a configuration in a YAML/JSON file.

## Installation
There are several ways to install this application:

1.  Download the archive from [GitHub][2] and extract it somewhere.
2.  Alternatively, clone this repository and run Gradle's `installDist` task.

        git clone https://github.com/Virtlink/gator.git
        cd gator/
        ./gradlew installDist

## Usage
Run the `gator.bat` (on Windows) or `gator` (on non-Windows) scripts from the `bin/` directory. For example:

    ./gator x.yaml y.yaml z.yaml

The arguments are the source YAML files with the data.




[1]: https://travis-ci.org/Virtlink/gator
[2]: https://github.com/Virtlink/gator/releases
[3]: https://virtlink.com/gator/
[4]: https://github.com/Virtlink/gator/commits/master
[5]: https://github.com/Virtlink/gator/blob/master/LICENSE
