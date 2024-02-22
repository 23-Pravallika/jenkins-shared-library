def call (){

    if (!env.TFDIR){
        env.TFDIR = "./"
    }

    properties([
        parameters([
            choice(choices: 'dev\nprod', description: "choose the environment", name: "ENV"),
            choice(choices: 'apply\ndestroy', description: "choose apply or destroy", name: "ACT"),
            string(choices: 'APP_VERSION', description: "Enter the Backend Version To Ve Deployed - Ignore this if it is a backend component", name: "APP_VERSION"),
        ]),
    ])
    node("Jenkins-WS") {
      ansiColor('xterm'){
         sh "rm -rf *"
        git branch: 'main', url: " https://github.com/23-Pravallika/${REPONAME}.git"

        stage('terraform init') {
            sh '''
                    cd ${TFDIR}            
                    terrafile -f ${ENV}-env/Terrafile
                    terraform init -backend-config=${ENV}-env/${ENV}-backend.tfvars
            '''
        }
        stage('terraform plan') {
            sh '''
                    cd ${TFDIR}
                    export TF_VAR_APP_VERSION=${APP_VERSION}
                    terraform plan -var-file=${ENV}-env/${ENV}.tfvars
            '''
        }
        stage("terraform ${ACT}"){
            sh '''
                    cd ${TFDIR}
                    export TF_VAR_APP_VERSION=${APP_VERSION}
                    terraform ${ACT} -var-file=${ENV}-env/${ENV}.tfvars -auto-approve
            '''
        }
    }
    }
}



