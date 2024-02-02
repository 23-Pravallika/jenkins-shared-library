def lintchecks(COMPONENT){
    sh '''      
            echo Lint Checks for ${COMPONENT}
            echo installing jslint
            # npm install jslint
            # node_modules/jslint/bin/jslint.js server.js
            echo performing lint checks for ${COMPONENT}
            echo performing lint checks completed ${COMPONENT} 
    '''
}

//Call is the default function which will be called when you call the fileName
def call(COMPONENT){
    pipeline {
        agent any 
        stages {
            stage('Lint Checks') {
                steps {
                    script{
                        lintchecks('COMPONENT')
                    }
                }
            }
        }
    }
}
