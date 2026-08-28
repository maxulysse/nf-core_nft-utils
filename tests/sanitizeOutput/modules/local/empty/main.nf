process EMPTY {
    label 'process_low'

    input:
    val input

    output:
    env 'MY_CONSTANT', emit: my_constant
    env 'EMPTY_ENV', emit: empty_env
    stdout emit: stdout
    eval ('echo Hello world!'), emit: eval_filled
    eval (''), emit: eval_empty

    script:
    """
    export MY_CONSTANT="This is a test"
    export EMPTY_ENV=""
    echo "${input}"
    """
}
