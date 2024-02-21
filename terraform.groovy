def call (){
    properties([
        parameters([
            choice(choices: 'dev\nprod', description: "choose the environment", name: "ENV"),
            choice(choices: 'apply\ndestroy', description: "choose apply or destroy", name: "ACT"),
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
                    terraform plan -var-file=${ENV}-env/${ENV}.tfvars
            '''
        }
        stage("terraform ${ACT}"){
            sh '''
                    cd ${TFDIR}
                    terraform ${ACT} -var-file=${ENV}-env/${ENV}.tfvars -auto-approve
            '''
        }
    }
    }
}



